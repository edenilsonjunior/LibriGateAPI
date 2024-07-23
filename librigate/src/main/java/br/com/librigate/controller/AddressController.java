package br.com.librigate.controller;

import br.com.librigate.model.dto.AddressDTO;
import br.com.librigate.model.entity.people.Address;
import br.com.librigate.model.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Cria um novo endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso."),
            @ApiResponse(responseCode = "404", description = "O cep digitado é invalido."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<Address> postAddress(@RequestBody AddressDTO dto) {

        try {
            Address address = addressService.create(dto);
            return new ResponseEntity<>(address, HttpStatus.CREATED);
        }
        catch (RestClientException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(){

        try {
            var address = addressService.findAll();

            if(address.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(address, HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable long id) {

        try {
            var address = addressService.findByPK(id);

            if(address.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Address> putAddress(@PathVariable long id, @RequestBody AddressDTO dto) {

        try {
            Address address = addressService.update(id, dto);
            return new ResponseEntity<>(address, HttpStatus.OK);

        }catch (InvalidParameterException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(InternalError ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<Address> deleteAddress(@PathVariable long id) {

        addressService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
