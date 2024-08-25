package br.com.librigate.dto.book;

public record StockDTO(
        String bookIsbn,
        String bookTitle,
        int quantity
) { }