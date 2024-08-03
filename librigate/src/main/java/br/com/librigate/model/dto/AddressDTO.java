package br.com.librigate.model.dto;

public record AddressDTO(
        String zipCode,
        int number,
        String complement)
{ }
