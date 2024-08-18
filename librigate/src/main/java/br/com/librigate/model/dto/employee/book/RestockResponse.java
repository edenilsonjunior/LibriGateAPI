package br.com.librigate.model.dto.employee.book;

import java.util.List;

public record RestockResponse(
        String id,
        String date,
        String employeeCpf,
        List<RestockBook> restockBooks
) { }
