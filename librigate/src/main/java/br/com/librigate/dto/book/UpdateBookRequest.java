package br.com.librigate.dto.book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record UpdateBookRequest(
        String isbn,
        Optional<String> title,
        Optional<String> description,
        Optional<String> publisher,
        Optional<String> category,
        Optional<List<String>> authorsName,
        Optional<Integer> edition,
        Optional<LocalDate> launchDate
) { }
