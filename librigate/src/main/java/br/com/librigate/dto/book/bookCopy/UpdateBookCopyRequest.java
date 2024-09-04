package br.com.librigate.dto.book.bookCopy;

import java.util.Optional;

public record UpdateBookCopyRequest(
        Long id,
        Optional<Double> price,
        Optional<String> status
) { }
