package com.wallet.wallet_service.domain.repository;

import java.util.Optional;

import com.wallet.wallet_service.domain.model.User;

public interface UsersRepository {
    User save(User user);

    Optional<User> findByDocument(String document);
}
