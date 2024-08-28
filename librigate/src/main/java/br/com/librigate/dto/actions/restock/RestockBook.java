package br.com.librigate.dto.actions.restock;

public record RestockBook(
        String isbn,
        int quantity,
        double unitValue
) { }
