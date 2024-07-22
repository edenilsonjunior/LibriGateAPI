package br.com.librigate.model.service;

import br.com.librigate.model.dto.AddressDTO;
import br.com.librigate.model.dto.ViaCepResponse;
import br.com.librigate.model.entity.people.Address;
import br.com.librigate.model.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;


    private final RestTemplate restTemplate = new RestTemplate();

    public Address saveAddress(Address address) {

        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }


    public Address populateAddress(AddressDTO addressDTO) {

        String url = "https://viacep.com.br/ws/" + addressDTO.zipCode() + "/json/";
        var response =  restTemplate.getForObject(url, ViaCepResponse.class);

        Address address = new Address();

        address.setId(UUID.randomUUID().toString());
        address.setZipCode(addressDTO.zipCode());
        address.setStreet(response.getLogradouro());
        address.setDistric(response.getBairro());
        address.setCity(response.getLocalidade());
        address.setState(response.getUf());
        address.setNumber(addressDTO.number());
        address.setComplement(addressDTO.complement());

        return address;
    }
}
