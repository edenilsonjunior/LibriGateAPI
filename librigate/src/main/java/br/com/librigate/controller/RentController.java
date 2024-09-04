package br.com.librigate.controller;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.model.service.interfaces.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rent")
public class RentController {

    private final IRentService rentService;

    @Autowired
    public RentController(IRentService rentService) {
        this.rentService = rentService;
    }

    
    @GetMapping("/{rentId}")
    public ResponseEntity<?> findRendById(@PathVariable Long rentId) {
        return rentService.findRendById(rentId);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> findRentsByCustomerCPF(@PathVariable String cpf) {
        return rentService.findRentsByCustomerCPF(cpf);
    }

    @PostMapping
    public ResponseEntity<?> rent(@RequestBody RentRequest request) {
        return rentService.rent(request);
    }

    @PutMapping("/devolution/{rentId}")
    public ResponseEntity<?> processDevolutionBook(@PathVariable Long rentId) {
        return rentService.processDevolutionBook(rentId);
    }

    @PutMapping("/renew/{rentId}")
    public ResponseEntity<?> renewRent(@PathVariable Long rentId) {
        return rentService.renewRent(rentId);
    }
}
