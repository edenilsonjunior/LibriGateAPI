package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.restock.RestockBook;
import br.com.librigate.dto.book.fisicalBook.CreateFisicalBookRequest;
import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Employee;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.service.book.FisicalBookService;
import br.com.librigate.model.repository.RestockRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RestockFactory {

    private final RestockRepository restockRepository;

    private final FisicalBookService fisicalBookService;
    private final FisicalBookRepository fisicalBookRepository;


    public RestockFactory(
        RestockRepository restockRepository,
        FisicalBookService fisicalBookService,
        FisicalBookRepository fisicalBookRepository
    ) {
        this.restockRepository = restockRepository;
        this.fisicalBookService = fisicalBookService;
        this.fisicalBookRepository = fisicalBookRepository;
    }

    public Restock createRestock(Employee employee, double totalPrice) {
        var restock = new Restock();
        restock.setEmployee(employee);
        restock.setRestockDate(LocalDate.now());
        restock.setPrice(totalPrice);
        return restockRepository.save(restock);
    }

    public FisicalBook createFisicalBook(String isbn, double price, Restock restock) {

        Long copyNumber = fisicalBookRepository.findMaxCopyNumberByBookIsbn(isbn);
        copyNumber = copyNumber == null ? 1 : copyNumber + 1;

        var createFKRequest = new CreateFisicalBookRequest(isbn, copyNumber,price, restock);
        return fisicalBookService.create(createFKRequest);
    }

    public RestockBook createFisicalBooksByIsbn(String isbn, int quantity, double price, Restock restock) {

        for (int i = 0; i < quantity; i++) {
            createFisicalBook(isbn, price, restock);
        }

        return new RestockBook(isbn, quantity, price);
    }
}
