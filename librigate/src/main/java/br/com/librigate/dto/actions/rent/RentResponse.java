package br.com.librigate.dto.actions.rent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public record RentResponse(
        Long id,
        String customerCpf,
        LocalDate rentDate,
        String status,
        LocalDate devolutionDate,
        Optional<LocalDateTime> givenBackAt,
        List<RentBook> books
) { }
