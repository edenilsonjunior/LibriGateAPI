package br.com.librigate.dto.actions.restock;

import java.time.LocalDate;
import java.util.List;

public record RestockResponse(
        Long id,
        String employeeCpf,
        String employeeName,
        Double totalPrice,
        LocalDate date,
        List<BookResponse> restockBooks
) { }
