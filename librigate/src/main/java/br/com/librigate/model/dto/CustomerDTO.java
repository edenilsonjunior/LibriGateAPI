package br.com.librigate.model.dto;

import java.time.LocalDate;

public record CustomerDTO(
        Long cpf,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String telephone,
        String password,
        LocalDate registrationDate,
        boolean active,
        AddressDTO address
) { }
