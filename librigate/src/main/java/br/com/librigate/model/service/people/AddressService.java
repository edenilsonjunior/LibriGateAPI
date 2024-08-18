package br.com.librigate.model.service.people;

import br.com.librigate.model.dto.AddressDTO;
import br.com.librigate.model.dto.ViaCepResponse;
import br.com.librigate.model.entity.people.Address;
import br.com.librigate.model.repository.AddressRepository;
import br.com.librigate.model.service.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    private final RestTemplate restTemplate = new RestTemplate();


    public Address create(AddressDTO dto) {

        try {
            Address address = populateAddress(dto);
            return addressRepository.save(address);

        } catch (RestClientException ex) {
            throw new InvalidParameterException("O cep digitado é invalido!");
        } catch (Exception ex) {
            throw new InternalError(ex.getMessage());
        }
    }


    public Optional<Address> findByPK(Long id) {
        return addressRepository.findById(id);
    }


    public List<Address> findAll() {
        return addressRepository.findAll();
    }


    public Address update(Long id, AddressDTO dto) {

        Address address = addressRepository.findById(id).orElseThrow(() -> new InvalidParameterException("Endereço não encontrado"));

        try {
            Address newAddress = populateAddress(dto);
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


    public void delete(Long id) {
        addressRepository.deleteById(id);
    }


    private Address populateAddress(AddressDTO addressDTO) throws RestClientException {

        String url = "https://viacep.com.br/ws/" + addressDTO.zipCode() + "/json/";

        var response = restTemplate.getForObject(url, ViaCepResponse.class);
        Address address = new Address();

        address.setZipCode(addressDTO.zipCode());
        address.setStreet(response.logradouro());
        address.setDistrict(response.bairro());
        address.setCity(response.localidade());
        address.setState(response.uf());
        address.setNumber(addressDTO.number());
        address.setComplement(addressDTO.complement());

        return address;
    }

}
