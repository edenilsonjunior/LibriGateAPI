package br.com.librigate.model.dto.customer.rent;

import java.util.List;

public record RentRequest(
        String customerCpf,
        List<String> booksIsbn
) { }


