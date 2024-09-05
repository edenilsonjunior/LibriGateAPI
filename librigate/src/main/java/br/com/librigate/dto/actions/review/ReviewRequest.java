package br.com.librigate.dto.actions.review;

public record ReviewRequest(
        String customerCpf,
        String bookIsbn,
        String description,
        double rating
) { }
