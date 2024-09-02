package br.com.librigate.dto.book;

import java.time.LocalDate;
import java.util.List;

public record NewBookRequest(
        String employeeCpf,
        String isbn,
        String title,
        String description,
        String publisher,
        String category,
        List<String> authorsName,
        int edition,
        LocalDate launchDate,
        double unityValue,
        int quantity
) { }
