package br.com.librigate.dto.authorization;

import br.com.librigate.model.entity.people.UserRole;

public record RegisterRequest(
        String login,
        String password,
        UserRole role
) { }
