package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.address.AddressRequest;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.address.Address;

public interface IAddressService {

     Address findById(Long id) throws EntityNotFoundException;
     Address create(AddressRequest request);
     Address update(Long id, AddressRequest request) throws EntityNotFoundException;
}
