package br.com.librigate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.model.service.interfaces.IRentService;

@RestController
@RequestMapping("/api/rent")
public class RentController {

    private final IRentService rentService;

    @Autowired
    public RentController(IRentService rentService) {
        this.rentService = rentService;
    }

    
    @GetMapping("/{rentId}")
    public ResponseEntity<?> getRendById(@PathVariable Long rentId) {
        return rentService.getRendById(rentId);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getRents(@PathVariable String cpf) {
        return rentService.getRents(cpf);
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
