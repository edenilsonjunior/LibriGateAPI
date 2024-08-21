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
    public ResponseEntity<?> findByPK(Long id) {
        return restockService.findByPK(id);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getRestockHistory() {
        return restockService.getRestockHistory();
    }

    @PostMapping("/restock-new-book")
    public ResponseEntity<?> buyNewBook(@RequestBody NewBookRequest request) {

        return restockService.buyNewBook(request);
    }

    @PostMapping("/restock")
    public ResponseEntity<?> restockBook(@RequestBody RestockBookRequest request) {

        return restockService.restockBook(request);
    }

}
