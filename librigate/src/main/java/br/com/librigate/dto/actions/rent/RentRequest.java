package br.com.librigate.dto.actions.rent;

import java.util.List;

public record RentRequest(
        String customerCpf,
        List<String> booksIsbn
) { }


