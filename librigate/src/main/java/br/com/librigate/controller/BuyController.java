package br.com.librigate.controller;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy")
public class BuyController {
    
    private final IBuyService buyService;

    @Autowired
    public BuyController(IBuyService buyService) {
        this.buyService = buyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByPK(@PathVariable Long id){
        return buyService.findByPK(id);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getPurchasesByCustomerCpf(@PathVariable String cpf){
        return buyService.getPurchasesByCustomerCpf(cpf);
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
