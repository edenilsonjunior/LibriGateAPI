package br.com.librigate.dto.actions.restock;

public record BookRequest(
    String isbn,
    int quantity,
    double unitValue
) { }
