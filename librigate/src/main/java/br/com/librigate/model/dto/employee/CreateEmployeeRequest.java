package br.com.librigate.model.dto.employee;

import br.com.librigate.model.dto.AddressRequest;

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
