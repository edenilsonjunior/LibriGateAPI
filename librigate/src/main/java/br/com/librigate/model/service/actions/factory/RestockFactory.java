package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.restock.RestockBook;
import br.com.librigate.dto.book.bookCopy.CreateBookCopyRequest;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.service.book.BookCopyService;
import br.com.librigate.model.repository.RestockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RestockFactory {

    private final RestockRepository restockRepository;
    private final BookCopyService bookCopyService;
    private final BookCopyRepository bookCopyRepository;


    @Autowired
    public RestockFactory(RestockRepository restockRepository,BookCopyService bookCopyService,BookCopyRepository bookCopyRepository) {
        this.restockRepository = restockRepository;
        this.bookCopyService = bookCopyService;
        this.bookCopyRepository = bookCopyRepository;
    }

    public Restock createRestock(Employee employee, double totalPrice) {
        var restock = new Restock();
        restock.setEmployee(employee);
        restock.setRestockDate(LocalDate.now());
        restock.setPrice(totalPrice);
        return restockRepository.save(restock);
    }

    public void createBookCopy(String isbn, double price, Restock restock) {

        Long copyNumber = bookCopyRepository.findMaxCopyNumberByBookIsbn(isbn);
        copyNumber = copyNumber == null ? 1 : copyNumber + 1;

        var createFKRequest = new CreateBookCopyRequest(isbn, copyNumber,price, restock);
        bookCopyService.create(createFKRequest);
    }

    public RestockBook createBookCopiesByIsbn(String isbn, int quantity, double price, Restock restock) {

        for (int i = 0; i < quantity; i++) {
            createBookCopy(isbn, price, restock);
        }

        return new RestockBook(isbn, quantity, price);
    }
}
