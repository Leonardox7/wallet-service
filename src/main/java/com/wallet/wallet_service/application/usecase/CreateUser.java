package com.wallet.wallet_service.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wallet.wallet_service.application.dto.CreateUserInput;
import com.wallet.wallet_service.application.dto.CreateUserOutput;
import com.wallet.wallet_service.domain.model.User;
import com.wallet.wallet_service.domain.repository.UsersRepository;
import com.wallet.wallet_service.exception.ConflictDataException;

@Service
public class CreateUser {

    private final UsersRepository usersRepository;

    public CreateUser(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public CreateUserOutput execute(CreateUserInput input) {
        Optional<User> userFounded = usersRepository.findByDocument(input.document());
        if (userFounded.isPresent()) {
            throw new ConflictDataException("User already exists with document: " + input.document());
        }
        User user = usersRepository.save(input.toUser());
        return new CreateUserOutput(user.getId().toString(), user.getWallet().getId().toString());
    }
}
