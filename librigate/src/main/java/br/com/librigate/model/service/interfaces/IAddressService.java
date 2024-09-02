package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.address.AddressRequest;
import br.com.librigate.model.entity.address.Address;

public interface IAddressService {

     Address create(AddressRequest dto);
     Address findByPK(Long id) throws EntityNotFoundException;
     Address update(Long id, AddressRequest dto) throws EntityNotFoundException;
}
