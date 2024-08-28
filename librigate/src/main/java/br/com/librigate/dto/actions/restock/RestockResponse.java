package br.com.librigate.dto.actions.restock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record RestockResponse(
        Long id,
        Double price,
        LocalDate date,
        String employeeCpf,
        List<RestockBook> restockBooks
) { }
