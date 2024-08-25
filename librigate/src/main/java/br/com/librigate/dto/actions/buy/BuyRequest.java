package br.com.librigate.dto.actions.buy;

import java.util.List;

public record BuyRequest(
        String customerCpf,
        List<BuyBook> books
) { }
