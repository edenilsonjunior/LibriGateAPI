package br.com.librigate.dto.book;

public record RestockBook(
        String isbn,
        int quantity,
        double unitValue
) { }
