package br.com.librigate.model.dto.employee;

import br.com.librigate.model.dto.AddressDTO;
import java.util.Optional;

public record UpdateEmployeeRequest(

    String cpf,
    Optional<String> firstName,
    Optional<String> lastName,
    Optional<String> telephone,
    Optional<String> password,
    Optional<AddressDTO> address,
    Optional<String> role
) { }
