package br.com.librigate.model.dto.customer;

import br.com.librigate.model.dto.AddressRequest;

import java.util.Optional;

public record UpdateCustomerRequest(
        String cpf,
        Optional<String> firstName,
        Optional<String> lastName,
        Optional<String> telephone,
        Optional<String> password,
        Optional<AddressRequest> address
) {
}
