package br.com.librigate.dto.actions.rent;

import java.time.LocalDate;
import java.util.Optional;

public record RentResponse(
        Long id,
        String customerCpf,
        LocalDate rentDate,
        String status,
        LocalDate devolutionDate,
        Optional<LocalDate> givenBackAt
) { }
