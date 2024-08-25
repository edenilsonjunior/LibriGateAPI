package br.com.librigate.dto.book.fisicalBook;

import br.com.librigate.model.entity.actions.Restock;

public record CreateFisicalBookRequest(

        String isbn,
        Long copyNumber,
        double price,
        Restock restock
) { }
