package br.com.librigate.model.dto.fisicalBook;

import br.com.librigate.model.entity.actions.Restock;

public record CreateFisicalBookRequest(

        String isbn,
        Long copyNumber,
        double price,
        Restock restock
) { }
