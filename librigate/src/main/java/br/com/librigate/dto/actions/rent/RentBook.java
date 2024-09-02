package br.com.librigate.dto.actions.rent;

public record RentBook(

        String isbn,
        Long copyNumber,
        String title,
        String status
) { }
