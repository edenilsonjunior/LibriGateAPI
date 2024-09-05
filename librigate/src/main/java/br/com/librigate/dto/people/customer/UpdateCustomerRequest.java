package br.com.librigate.dto.people.customer;

import br.com.librigate.dto.address.AddressRequest;

import java.util.Optional;

public record UpdateCustomerRequest(
        String cpf,
        Optional<String> firstName,
        Optional<String> lastName,
        Optional<String> telephone,
        Optional<AddressRequest> address
) {
}
