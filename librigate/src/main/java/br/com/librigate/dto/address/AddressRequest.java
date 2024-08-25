package br.com.librigate.dto.address;

public record AddressRequest(
        String zipCode,
        int number,
        String complement
) { }
