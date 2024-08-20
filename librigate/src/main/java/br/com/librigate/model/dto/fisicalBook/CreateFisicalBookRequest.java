package br.com.librigate.model.dto.fisicalBook;

public record CreateFisicalBookRequest(

        String isbn,
        Long copyNumber,
        double price,
        Long restockId
) { }
