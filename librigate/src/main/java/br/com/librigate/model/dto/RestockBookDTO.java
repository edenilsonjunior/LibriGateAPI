package br.com.librigate.model.dto;

public record RestockBookDTO(
        String isbn,
        int quantity
) { }
