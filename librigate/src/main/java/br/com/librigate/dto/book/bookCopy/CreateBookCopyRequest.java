package br.com.librigate.dto.book.bookCopy;

import br.com.librigate.model.entity.actions.Restock;

public record CreateBookCopyRequest(
        String isbn,
        Long copyNumber,
        double price,
        Restock restock
) { }
