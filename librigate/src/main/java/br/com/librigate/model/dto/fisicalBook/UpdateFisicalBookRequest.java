package br.com.librigate.model.dto.fisicalBook;

import java.util.Optional;

public record UpdateFisicalBookRequest(
        Long id,
        Optional<Double> price,
        Optional<String> status,
        Optional<Long> buyId
) { }
