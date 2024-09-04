package br.com.librigate.dto.actions.restock;

public record RestockBook(
        String isbn,
        String bookTitle,
        int quantity,
        double unitValue
) { }
