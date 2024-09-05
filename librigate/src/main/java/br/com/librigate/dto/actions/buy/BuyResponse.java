package br.com.librigate.dto.actions.buy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record BuyResponse(
        Long id,
        String customerCpf,
        String customerName,
        LocalDateTime buyDate,
        LocalDate dueDate,
        Optional<LocalDateTime> paidAt,
        Double totalPrice,
        String status,
        List<BuyBook> books
) { }
