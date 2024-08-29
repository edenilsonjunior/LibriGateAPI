package br.com.librigate.controller;

import br.com.librigate.model.service.interfaces.IBookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book-copy")
public class BookCopyController {

    @Autowired
    private IBookCopyService bookCopyService;

    @GetMapping("/stock")
    public ResponseEntity<?> getStock() {
        return bookCopyService.getStock();
    }

    @GetMapping("/stock/book/{isbn}")
    public ResponseEntity<?> getStockByBook(@PathVariable String isbn) {
        return bookCopyService.getStockByBook(isbn);
    }
}
