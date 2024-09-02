package br.com.librigate.dto.actions.buy;

public record BuyBook(
        String isbn,
        int quantity
) { }
