package br.com.librigate.model.service.actions.factory;

import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.service.book.BookCopyService;
import br.com.librigate.model.repository.RentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RentFactory {

    private final RentRepository rentRepository;
    private final BookCopyRepository bookCopyRepository;

    public RentFactory(
            RentRepository rentRepository,
            BookCopyService bookCopyService,
            BookCopyRepository bookCopyRepository) {
        this.rentRepository = rentRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public Rent createRent(Customer customer) {
        Rent rent = new Rent();
        rent.setCustomer(customer);
        rent.setRentDate(LocalDate.now());
        rent.setStatus("RENTED");
        rent.setDevolutionDate(LocalDate.now().plusWeeks(1));
        return rentRepository.save(rent);
    }

    public List<BookCopy> associateBooksToRent(List<BookCopy> books, Rent rent) {

        books.forEach(book ->{
            book.setStatus("RENTED");
        });

        return books;
    }

    public List<BookCopy> getAvailableBooksForRent(List<String> isbns) {
        var list = new ArrayList<BookCopy>();

        isbns.forEach(isbn -> {
            var book = bookCopyRepository.findAllAvailableByIsbn(isbn);
            list.add(book.getFirst());
        });

        return list;
    }
}
