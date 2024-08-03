package br.com.librigate.model.dto;

import java.time.LocalDate;

public record FisicalBookDTO(
        String isbn,
        String title,
        String description,
        String publisher,
        String category,
        String[] authorsName,
        int edition,
        LocalDate launchDate,
        int copyNumber,
        String status,
        double price
) { }