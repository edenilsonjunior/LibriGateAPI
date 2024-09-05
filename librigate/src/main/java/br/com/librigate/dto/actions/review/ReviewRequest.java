package br.com.librigate.dto.actions.review;

public record ReviewRequest(
        String cpf,
        String bookIsbn,
        String description,
        double rating
) { }
