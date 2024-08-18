package br.com.librigate.model.dto.employee.book;

public record RestockBook(
        String isbn,
        int quantity,
        double unitValue
) { }
