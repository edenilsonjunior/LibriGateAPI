package br.com.librigate.dto.actions.restock;

import java.util.List;

public record RestockBookRequest(
        String employeeCpf,
        List<BookRequest> books
) { }
