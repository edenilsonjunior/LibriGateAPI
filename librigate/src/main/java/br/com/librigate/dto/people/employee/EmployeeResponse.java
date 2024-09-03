package br.com.librigate.dto.people.employee;

import br.com.librigate.model.entity.address.Address;

import java.time.LocalDate;

public record EmployeeResponse(
        String cpf,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        boolean active,
        String role,
        Address address
) { }
