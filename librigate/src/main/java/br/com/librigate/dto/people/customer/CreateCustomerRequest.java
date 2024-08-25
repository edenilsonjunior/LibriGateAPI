package br.com.librigate.dto.people.customer;

import br.com.librigate.dto.address.AddressRequest;

import java.time.LocalDate;

public record CreateCustomerRequest(
        String cpf,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String telephone,
        String password,
        AddressRequest address
) { }
