package br.com.librigate.dto.authorization;

public record AuthenticationRequest(
        String login,
        String password
) { }
