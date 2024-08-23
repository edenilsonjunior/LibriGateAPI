package br.com.librigate.model.dto.employee;

import br.com.librigate.model.dto.AddressRequest;

import java.util.Optional;

public record UpdateEmployeeRequest(

    String cpf,
    Optional<String> firstName,
    Optional<String> lastName,
    Optional<String> telephone,
    Optional<String> password,
    Optional<AddressRequest> address,
    Optional<String> role
) { }
