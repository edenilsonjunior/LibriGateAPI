package br.com.librigate.controller;

import br.com.librigate.model.service.interfaces.IBookCopyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "book-copy")
@RestController
@RequestMapping("/api/book-copy")
public class BookCopyController {

    @Autowired
    private IBookCopyService bookCopyService;

    @GetMapping("/stock")
    public ResponseEntity<?> findStock() {
        return bookCopyService.findStock();
    }

    @GetMapping("/stock/{isbn}")
    public ResponseEntity<?> findStockByBook(@PathVariable String isbn) {
        return bookCopyService.findStockByBook(isbn);
    }
}
