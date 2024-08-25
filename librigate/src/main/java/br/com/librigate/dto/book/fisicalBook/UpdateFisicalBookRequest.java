package br.com.librigate.dto.book.fisicalBook;

import java.util.Optional;

public record UpdateFisicalBookRequest(
        Long id,
        Optional<Double> price,
        Optional<String> status,
        Optional<Long> buyId
) { }
