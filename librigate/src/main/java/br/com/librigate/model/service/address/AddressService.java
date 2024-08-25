package br.com.librigate.model.service.address;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.dto.address.AddressRequest;
import br.com.librigate.dto.address.ViaCepResponse;
import br.com.librigate.model.entity.address.Address;
import br.com.librigate.model.repository.AddressRepository;
import br.com.librigate.model.service.interfaces.IAddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;

@Service
public class AddressService implements IAddressService{

    @Autowired
    private AddressRepository addressRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    @Override
    public Address create(AddressRequest request) {

        try {
            var address = populateAddress(request);
            return addressRepository.save(address);

        } catch (RestClientException ex) {
            throw new InvalidParameterException("O cep digitado é invalido!");
        } catch (Exception ex) {
            throw new InternalError(ex.getMessage());
        }
    }


    @Override
    public Address findByPK(Long id) throws EntityNotFoundException {
        return addressRepository
            .findById(id)
            .orElseThrow(()->new EntityNotFoundException("Endereço não encontrado"));
    }

    @Transactional
    @Override
    public Address update(Long id, AddressRequest dto) throws EntityNotFoundException {

        var address = findByPK(id);

        try {
            var newAddress = populateAddress(dto);
            address.setId(id);
            address.setZipCode(newAddress.getZipCode());
            address.setStreet(newAddress.getStreet());
            address.setDistrict(newAddress.getDistrict());
            address.setCity(newAddress.getCity());
            address.setState(newAddress.getState());
            address.setNumber(newAddress.getNumber());
            address.setComplement(newAddress.getComplement());

            return addressRepository.save(address);
        } catch (Exception ex) {
            throw new InternalError(ex.getMessage());
        }
    }


    private Address populateAddress(AddressRequest addressRequest) throws RestClientException {

        String url = "https://viacep.com.br/ws/" + addressRequest.zipCode() + "/json/";

        var response = restTemplate.getForObject(url, ViaCepResponse.class);
        var address = new Address();

        address.setZipCode(addressRequest.zipCode());
        address.setStreet(response.logradouro());
        address.setDistrict(response.bairro());
        address.setCity(response.localidade());
        address.setState(response.uf());
        address.setNumber(addressRequest.number());
        address.setComplement(addressRequest.complement());

        return address;
    }

}
