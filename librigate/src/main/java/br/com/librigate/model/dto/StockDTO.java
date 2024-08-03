package br.com.librigate.model.dto;

public record StockDTO(
        String bookIsbn,
        String bookTitle,
        int quantity
) { }
