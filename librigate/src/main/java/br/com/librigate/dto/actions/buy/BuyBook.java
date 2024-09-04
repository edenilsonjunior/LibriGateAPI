package br.com.librigate.dto.actions.buy;

public record BuyBook(
        String isbn,
        String title,
        int quantity
) { }
