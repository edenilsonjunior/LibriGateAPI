package br.com.librigate.model.service.address;

import br.com.librigate.dto.address.AddressRequest;
import br.com.librigate.dto.address.ViaCepResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.entity.address.Address;
import br.com.librigate.model.repository.AddressRepository;
import br.com.librigate.model.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class AddressService implements IAddressService {

    private final AddressRepository addressRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address findById(Long id) throws EntityNotFoundException {
        return addressRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
    }


    @Override
    public Address create(AddressRequest request) {

        var address = populateAddress(request);
        return addressRepository.save(address);
    }

     
    @Override
    public Address update(Long id, AddressRequest request) throws EntityNotFoundException {

        var address = findById(id);

        var newAddress = populateAddress(request);
        address.setZipCode(newAddress.getZipCode());
        address.setStreet(newAddress.getStreet());
        address.setDistrict(newAddress.getDistrict());
        address.setCity(newAddress.getCity());
        address.setState(newAddress.getState());
        address.setNumber(newAddress.getNumber());
        address.setComplement(newAddress.getComplement());

        return addressRepository.save(address);
    }

    private Address populateAddress(AddressRequest addressRequest) throws RestClientException {
        try {
            String zipCode = addressRequest.zipCode()
                    .replace("-", "")
                    .replace(".", "");

            String url = "https://viacep.com.br/ws/" + zipCode + "/json/";

            var response = restTemplate.getForObject(url, ViaCepResponse.class);

            if (response == null || response.logradouro() == null)
                throw new ValidationException("Invalid or non-existent ZIP code: " + addressRequest.zipCode());

            return buildAddress(response, zipCode, addressRequest);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
                throw new ValidationException("Invalid or non-existent ZIP code: " + addressRequest.zipCode());
        }
    }


    private Address buildAddress(ViaCepResponse response, String zipCode, AddressRequest addressRequest) {

        var address = new Address();
        address.setZipCode(zipCode);
        address.setStreet(response.logradouro());
        address.setDistrict(response.bairro());
        address.setCity(response.localidade());
        address.setState(response.uf());
        address.setNumber(addressRequest.number());
        address.setComplement(addressRequest.complement());

        return address;
    }

}
