package br.com.librigate.model.dto.customer.buy;

import java.time.LocalDate;
import java.util.Optional;

public record BuyResponse(
        Long id,
        String totalPrice,
        LocalDate dueDate,
        Optional<LocalDate> paidAt,
        String status
) { }
