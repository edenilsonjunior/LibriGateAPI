package br.com.librigate.dto.people.employee;

import br.com.librigate.dto.address.AddressRequest;

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
