package br.com.librigate.dto.book.bookCopy;

public record StockResponse(
     String isbn,
     String title,
     Long quantity
) { }
