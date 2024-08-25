package br.com.librigate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.model.service.interfaces.IBuyService;

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

    @PostMapping
    public ResponseEntity<?> purchase(@RequestBody BuyRequest request){
        return buyService.purchase(request);
    }

    @PostMapping("/processPayment/{buyId}")
    public ResponseEntity<?> processPayment(@PathVariable Long buyId){
        return buyService.processPayment(buyId);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getPurchases(@PathVariable String cpf){
        return buyService.getPurchases(cpf);
    }

    @GetMapping("/cpf/{cpf}/{paymentId}")
    public ResponseEntity<?> getPurchaseById(@PathVariable String cpf, @PathVariable Long paymentId){
        return buyService.getPurchaseById(cpf, paymentId);
    }

    @DeleteMapping("/{buyId}")
    public ResponseEntity<?> cancelPurchase(@PathVariable Long buyId){
        return buyService.cancelPurchase(buyId);
    }

}
