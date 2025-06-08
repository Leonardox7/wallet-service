package com.wallet.wallet_service.application.dto;

import com.wallet.wallet_service.domain.model.User;
import com.wallet.wallet_service.exception.InputDataException;

public record CreateUserInput(String name, String document) {

    public CreateUserInput {
        if (name == null || name.isBlank()) {
            throw new InputDataException("Name cannot be null or blank");
        }
        if (document == null || document.isBlank()) {
            throw new InputDataException("Document cannot be null or blank");
        }
        if (document.length() > 14 || document.length() < 11) {
            throw new InputDataException("Document must be 11 to 14 characters long");
        }
    }

    public User toUser() {
        return new User(this.name(), this.document());
    }
}
