package br.com.librigate.controller;


import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.dto.book.RestockBookRequest;
import br.com.librigate.model.service.interfaces.IRestockService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
