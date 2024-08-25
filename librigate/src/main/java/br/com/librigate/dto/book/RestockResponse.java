package br.com.librigate.dto.book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record RestockResponse(
        Long id,
        Optional<Double> price,
        LocalDate date,
        String employeeCpf,
        List<RestockBook> restockBooks
) { }
