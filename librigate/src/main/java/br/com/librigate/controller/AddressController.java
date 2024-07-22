package br.com.librigate.controller;

import br.com.librigate.model.dto.AddressDTO;
import br.com.librigate.model.entity.people.Address;
import br.com.librigate.model.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> PostAddress(@RequestBody AddressDTO addressDTO) {

        Address address = new AddressService().populateAddress(addressDTO);
        addressService.saveAddress(address);

        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }
}
