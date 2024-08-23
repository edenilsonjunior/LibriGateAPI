package br.com.librigate.model.dto.customer;

import br.com.librigate.model.dto.AddressRequest;

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
