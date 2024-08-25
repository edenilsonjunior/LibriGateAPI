package br.com.librigate.controller;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.UpdateCustomerRequest;
import br.com.librigate.model.service.interfaces.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest request){
        return customerService.create(request);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request){
        return customerService.update(request);
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String cpf){
        return customerService.delete(cpf);
    }

    @GetMapping("/find/{cpf}")
    public ResponseEntity<?> findCustomer(@PathVariable String cpf){
        return customerService.findByPK(cpf);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllCustomers(){
        return customerService.findAll();
    }

}
