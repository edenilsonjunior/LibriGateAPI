package br.com.librigate.model.service.actions.factory;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.service.book.BookCopyService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RentFactory {

    private final BookCopyRepository bookCopyRepository;

    public RentFactory(
            BookCopyService bookCopyService,
            BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    public Rent createRent(Customer customer) {
        Rent rent = new Rent();
        rent.setCustomer(customer);
        rent.setRentDate(LocalDate.now());
        rent.setStatus("RENTED");
        rent.setDevolutionDate(LocalDate.now().plusWeeks(1));
        return rent;
    }

    public List<BookCopy> associateBooksToRent(List<BookCopy> books, Rent rent) {

        return books.stream().peek(book ->{
            book.setStatus("RENTED");
        }).toList();
    }

    public List<BookCopy> getAvailableBooksForRent(List<String> isbns) {
        var list = new ArrayList<BookCopy>();

        isbns.forEach(isbn -> {
            var books = bookCopyRepository.findAllAvailableByIsbn(isbn);

            if(books.isEmpty())
                throw new EntityNotFoundException("There is not available books for isbn: " + isbn);

            var book = books.get(0);
            list.add(book);
        });

        return list;
    }

    public List<BookCopy> restoreBooks(Rent rent) {

        return rent.getBookList().stream().peek(book -> {
            book.setStatus("AVAILABLE");
        }).toList();
    }


}
