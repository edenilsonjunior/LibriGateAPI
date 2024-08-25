package br.com.librigate.dto.book;

import java.util.List;

public record RestockBookRequest(
        String employeeCpf,
        List<RestockBook> books
) { }
