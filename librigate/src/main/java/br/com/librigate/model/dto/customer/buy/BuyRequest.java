package br.com.librigate.model.dto.customer.buy;

import java.util.List;

public record BuyRequest(
        String customerCpf,
        List<BuyBook> books
) { }
