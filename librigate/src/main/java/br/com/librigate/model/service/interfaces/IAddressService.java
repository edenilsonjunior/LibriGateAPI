package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.AddressDTO;
import br.com.librigate.model.entity.people.Address;

public interface IAddressService {

     Address create(AddressDTO dto);

     Address findByPK(Long id) throws EntityNotFoundException;

     Address update(Long id, AddressDTO dto) throws EntityNotFoundException;
}
