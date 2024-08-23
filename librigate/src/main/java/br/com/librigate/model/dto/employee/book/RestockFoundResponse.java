package br.com.librigate.model.dto.employee.book;

import java.time.LocalDate;
import java.util.List;

public record RestockFoundResponse(
        double price,
        LocalDate date,
        String employeeCpf,
        List<RestockBook> restockBooks
) { }