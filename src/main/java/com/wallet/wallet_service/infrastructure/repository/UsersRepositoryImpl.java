package com.wallet.wallet_service.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wallet.wallet_service.domain.model.User;
import com.wallet.wallet_service.domain.repository.UsersRepository;
import com.wallet.wallet_service.infrastructure.constant.DbConstant;
import com.wallet.wallet_service.infrastructure.entity.UserEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UsersRepositoryImpl implements UsersRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public UsersRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public User save(User user) {
        entityManager.persist(new UserEntity(user));
        entityManager.flush();
        log.info("User saved: {}", user.getId());
        return user;
    }

    @Override
    public Optional<User> findByDocument(String document) {
        Optional<UserEntity> userEntity = entityManager.createQuery(DbConstant.FIND_USER_BY_DOCUMENT, UserEntity.class)
                .setParameter("document", document)
                .getResultStream()
                .findFirst();
        return userEntity.isPresent() ? Optional.of(userEntity.get().toModel()) : Optional.empty();
    }

}
