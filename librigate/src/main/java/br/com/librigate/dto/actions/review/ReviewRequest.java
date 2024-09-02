package br.com.librigate.dto.actions.review;

public record ReviewRequest(
        String cpf,
        String bookId,
        String description,
        double rating
) { }
