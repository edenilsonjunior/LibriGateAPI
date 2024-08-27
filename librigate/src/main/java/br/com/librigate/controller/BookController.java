package br.com.librigate.controller;

import br.com.librigate.model.service.interfaces.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("isbn/{bookIsbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String bookIsbn) {
        return bookService.getBookByIsbn(bookIsbn);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/review/{bookIsbn}")
    public ResponseEntity<?> getReview(@PathVariable String bookIsbn) {
        return bookService.getReview(bookIsbn);
    }
}
