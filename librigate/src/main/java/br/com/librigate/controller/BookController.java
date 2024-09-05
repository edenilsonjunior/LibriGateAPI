package br.com.librigate.controller;

import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.model.service.interfaces.IBookService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "book")
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private IBookService bookService;


    @GetMapping
    public ResponseEntity<?> findAll(){
        return bookService.findAll();
    }

    @GetMapping("isbn/{bookIsbn}")
    public ResponseEntity<?> findBookByIsbn(@PathVariable String bookIsbn) {
        return bookService.findBookByIsbn(bookIsbn);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> findBooksByCategory(@PathVariable String category) {
        return bookService.findBooksByCategory(category);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> findBooksByAuthor(@PathVariable String author) {
        return bookService.findBooksByAuthor(author);
    }

    @GetMapping("/review/{bookIsbn}")
    public ResponseEntity<?> findReview(@PathVariable String bookIsbn) {
        return bookService.findReview(bookIsbn);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateBookRequest request) {
        return bookService.update(request);
    }
}
