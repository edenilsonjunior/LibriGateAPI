package br.com.librigate.model.dto.customer.review;

public record ReviewRequest(
        String cpf,
        String bookId,
        String description,
        double rating
) { }
