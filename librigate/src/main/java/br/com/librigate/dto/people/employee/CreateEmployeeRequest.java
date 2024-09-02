package br.com.librigate.dto.people.employee;

import br.com.librigate.dto.address.AddressRequest;

import java.time.LocalDate;

public record CreateEmployeeRequest(
        String cpf,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String telephone,
        String role,
        String password,
        AddressRequest address
) { }
