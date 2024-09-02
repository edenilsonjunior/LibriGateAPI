package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.service.book.FisicalBookService;
import br.com.librigate.model.repository.RentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RentFactory {

    private final RentRepository rentRepository;
    private final FisicalBookService fisicalBookService;
    private final FisicalBookRepository fisicalBookRepository;

    public RentFactory(
            RentRepository rentRepository,
            FisicalBookService fisicalBookService,
            FisicalBookRepository fisicalBookRepository
    ) {
        this.rentRepository = rentRepository;
        this.fisicalBookService = fisicalBookService;
        this.fisicalBookRepository = fisicalBookRepository;
    }

    public Rent createRent(Customer customer) {
        Rent rent = new Rent();
        rent.setCustomer(customer);
        rent.setRentDate(LocalDate.now());
        rent.setStatus("RENTED");
        rent.setDevolutionDate(LocalDate.now().plusWeeks(1));
        return rentRepository.save(rent);
    }

    public List<FisicalBook> associateBooksToRent(List<FisicalBook> books, Rent rent) {
        books.forEach(book -> {
            book.setStatus("RENTED");
            book.setRent(rent);
            fisicalBookService.updateBook(book);
        });
        return books;
    }

    public List<FisicalBook> getAvailableBooksForRent(List<String> isbns) {
        return isbns.stream()
                .flatMap(isbn -> fisicalBookRepository.findAvailableByIsbn(isbn).stream())
                .toList();
    }
}
