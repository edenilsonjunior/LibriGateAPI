package br.com.librigate.dto.actions.buy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public record BuyResponse(
        Long id,
        String customerCpf,
        Double totalPrice,
        LocalDateTime buyDate,
        LocalDate dueDate,
        Optional<LocalDateTime> paidAt,
        String status
) { }
