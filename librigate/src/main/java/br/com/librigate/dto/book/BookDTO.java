package br.com.librigate.dto.book;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class BookDTO {
    private final String personCpf;
    private final String isbn;
    private final String title;
    private final String description;
    private final String publisher;
    private final String category;
    private final List<String> authorsName;
    private final int edition;
    private final LocalDate launchDate;
    private final double price;
    private final Long quantity;
}

