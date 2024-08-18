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
public class AddressService implements IService<Address, AddressDTO, Long> {

    @Autowired
    private AddressRepository addressRepository;
    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public Address create(AddressDTO dto) {

        try {
            Address address = populateAddress(dto);
            return addressRepository.save(address);

        }catch (RestClientException ex){
            throw new InvalidParameterException("O cep digitado é invalido!");
        }
        catch (Exception ex){
            throw new InternalError(ex.getMessage());
        }
    }


    @Override
    public Optional<Address> findByPK(Long id) {
        return addressRepository.findById(id);
    }


    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }


    @Override
    public Address update(AddressDTO dto){

        if(addressRepository.existsById(id)) {

            try {
                Address address = populateAddress(dto);
                return addressRepository.save(address);
            }catch (RestClientException ex){
                throw new InvalidParameterException("O cep digitado é invalido!");
            }
            catch (Exception ex){
                throw new InternalError(ex.getMessage());
            }
        }

        throw new InvalidParameterException("O id não condiz com nenhum endereço!");
    }


    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }


    private Address populateAddress(AddressDTO addressDTO) throws RestClientException {

        String url = "https://viacep.com.br/ws/" + addressDTO.zipCode() + "/json/";

        var response =  restTemplate.getForObject(url, ViaCepResponse.class);
        Address address = new Address();

        address.setZipCode(addressDTO.zipCode());
        address.setStreet(response.getLogradouro());
        address.setDistrict(response.getBairro());
        address.setCity(response.getLocalidade());
        address.setState(response.getUf());
        address.setNumber(addressDTO.number());
        address.setComplement(addressDTO.complement());

        return address;
    }

}
