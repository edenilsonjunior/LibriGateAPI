package br.com.librigate.model.service.interfaces;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.dto.AddressDTO;
import br.com.librigate.model.entity.people.Address;

public interface IAddressService {

     public Address create(AddressDTO dto);

     public Address findByPK(Long id) throws EntityNotFoundException;

     public Address update(Long id, AddressDTO dto) throws EntityNotFoundException;
}
