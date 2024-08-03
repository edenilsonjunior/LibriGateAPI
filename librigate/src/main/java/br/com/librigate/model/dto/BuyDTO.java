package br.com.librigate.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public record BuyDTO(
        Long id,
        LocalDate buyDate,
        boolean status,
        LocalDate devolutionDate,
        Optional<LocalDateTime> givenBackAt
) { }
