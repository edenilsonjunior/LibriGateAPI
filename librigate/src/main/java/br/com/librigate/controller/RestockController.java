package br.com.librigate.controller;


import br.com.librigate.dto.actions.restock.RestockBookRequest;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.model.service.interfaces.IRestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restock")
public class RestockController {

    @Autowired
    private IRestockService restockService;

    @GetMapping
    public ResponseEntity<?> findRestockHistory() {
        return restockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return restockService.findById(id);
    }

    @PostMapping("/restock-new-book")
    public ResponseEntity<?> buyNewBook(@RequestBody NewBookRequest request) {
        return restockService.buyNewBook(request);
    }

    @PostMapping("/restock-existing-books")
    public ResponseEntity<?> restockBook(@RequestBody RestockBookRequest request) {
        return restockService.restockBook(request);
    }

}
