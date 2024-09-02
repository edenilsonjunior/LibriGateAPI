package br.com.librigate.dto.actions.review;

public record ReviewResponse(
        String customerCpf,
        String bookIsbn,
        String description,
        double rating
) {
}
