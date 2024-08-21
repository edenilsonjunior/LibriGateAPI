package br.com.librigate.model.dto;

public record AddressRequest(
        String zipCode,
        int number,
        String complement
) { }
