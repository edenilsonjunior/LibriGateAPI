package br.com.librigate.controller;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.model.service.interfaces.IBuyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "buy")
@RestController
@RequestMapping("/api/buy")
public class BuyController {
    
    private final IBuyService buyService;

    @Autowired
    public BuyController(IBuyService buyService) {
        this.buyService = buyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return buyService.findById(id);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> findPurchasesByCustomerCpf(@PathVariable String cpf){
        return buyService.findPurchasesByCustomerCpf(cpf);
    }

    @PostMapping
    public ResponseEntity<?> purchase(@RequestBody BuyRequest request){
        return buyService.purchase(request);
    }

    @PostMapping("/process/{buyId}")
    public ResponseEntity<?> processPayment(@PathVariable Long buyId){
        return buyService.processPayment(buyId);
    }

    @DeleteMapping("/{buyId}")
    public ResponseEntity<?> cancelPurchase(@PathVariable Long buyId){
        return buyService.cancelPurchase(buyId);
    }

}
