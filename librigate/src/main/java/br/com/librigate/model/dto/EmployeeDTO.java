package br.com.librigate.model.dto;

import java.time.LocalDate;

public record EmployeeDTO(
        String cpf,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        String telephone,
        String role,
        String password,
        AddressDTO address
) {
}
