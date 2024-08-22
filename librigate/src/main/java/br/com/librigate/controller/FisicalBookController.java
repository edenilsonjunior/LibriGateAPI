package br.com.librigate.controller;

import br.com.librigate.model.service.interfaces.IFisicalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book/fisical")
public class FisicalBookController {

    @Autowired
    private IFisicalBookService fisicalBookService;

    @GetMapping("/stock")
    public ResponseEntity<?> getStock() {
        return fisicalBookService.getStock();
    }

    @GetMapping("/stock/book/{isbn}")
    public ResponseEntity<?> getStockByBook(@PathVariable String isbn) {
        return fisicalBookService.getStockByBook(isbn);
    }
}
