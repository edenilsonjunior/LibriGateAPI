package br.com.librigate.dto.actions.restock;

public record BookResponse(
        String isbn,
        String bookTitle,
        int quantity,
        double unitValue
) { }
