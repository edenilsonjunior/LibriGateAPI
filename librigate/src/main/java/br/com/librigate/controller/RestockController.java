package br.com.librigate.controller;


import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.service.interfaces.IRestockService;
import br.com.librigate.model.dto.employee.book.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

@RestController
@RequestMapping("/api/restock")
public class RestockController {

    @Autowired
    private IRestockService restockService;


    @GetMapping("/find/{id}")
    public Restock findByPK(Long id) {
        return restockService.findByPK(id);
    }

    @GetMapping("/history")
    public List<RestockResponse> getRestockHistory() {
        return restockService.getRestockHistory();
    }

    @PostMapping("/restock-new-book")
    public ResponseEntity<RestockResponse> buyNewBook(@RequestBody NewBookRequest request) {

        var response = restockService.buyNewBook(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/restock")
    public ResponseEntity<RestockResponse> restockBook(@RequestBody RestockBookRequest request) {
        var response =  restockService.restockBook(request);

        return ResponseEntity.ok(response);
    }

}
