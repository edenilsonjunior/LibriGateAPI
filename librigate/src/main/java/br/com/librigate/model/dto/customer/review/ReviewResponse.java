package br.com.librigate.model.dto.customer.review;

public record ReviewResponse(
        String customerCpf,
        String bookIsbn,
        String description,
        double rating
) {
}
