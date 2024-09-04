package br.com.librigate.dto.people.customer;

import br.com.librigate.model.entity.address.Address;

import java.time.LocalDate;

public record CustomerResponse(

        String cpf,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        LocalDate registrationDate,
        boolean active,
        Address address
) { }
