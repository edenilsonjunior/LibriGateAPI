package br.com.librigate.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public record RentDTO(
        Long id,
        LocalDate rentDate,
        String status,
        LocalDate devolutionDate,
        Optional<LocalDateTime> givenBackAt
) { }
