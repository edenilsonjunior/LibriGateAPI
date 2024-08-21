package br.com.librigate.model.dto.book;

import java.time.LocalDate;

public record CreateBookRequest(
        String isbn,
        String title,
        String description,
        String publisher,
        String category,
        String[] authorsName,
        int edition,
        LocalDate launchDate
){ }

