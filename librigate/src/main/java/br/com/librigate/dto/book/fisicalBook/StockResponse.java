package br.com.librigate.dto.book.fisicalBook;

public record StockResponse(
     String isbn,
     String title,
     Long quantity
) { }
