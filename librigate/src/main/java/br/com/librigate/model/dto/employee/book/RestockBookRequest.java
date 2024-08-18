package br.com.librigate.model.dto.employee.book;

import java.util.List;

public record RestockBookRequest(
        String employeeCpf,
        List<RestockBook> books
) { }
