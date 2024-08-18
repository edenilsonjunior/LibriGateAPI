package br.com.librigate.model.dto.employee.book;

import java.time.LocalDate;
import java.util.List;

public record RestockResponse(
        Long id,
        LocalDate date,
        String employeeCpf,
        List<RestockBook> restockBooks
) { }
