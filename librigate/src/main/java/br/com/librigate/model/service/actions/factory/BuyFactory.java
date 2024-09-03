package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BookCopyRepository;
import br.com.librigate.model.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BuyFactory {

    private final BuyRepository buyRepository;
    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public BuyFactory(BuyRepository buyRepository, BookCopyRepository bookCopyRepository) {
        this.buyRepository = buyRepository;
        this.bookCopyRepository = bookCopyRepository;
    }


    public Buy createBuy(BuyRequest request, Customer customer) {

        var entity = new Buy();
        entity.setCustomer(customer);
        entity.setBuyDate(LocalDateTime.now());
        entity.setDueDate(LocalDate.now().plusDays(2));
        entity.setStatus("PENDING");
        entity.setBooks(new ArrayList<>());

        return buyRepository.save(entity);
    }


    public List<BookCopy> soldBooks(BuyRequest request, Buy buy, Map<String, List<BookCopy>> availableBooks) {

        return request.books()
                .stream()
                .flatMap(buyBook -> {

                    var books = availableBooks.get(buyBook.isbn());
                    return createBooksByQuantity(books, buyBook.quantity(), buy).stream();

                })
                .collect(Collectors.toList());
    }


    public void approvePayment(Buy buy){

        buy.setStatus("APPROVED");
        buy.setPaidAt(LocalDateTime.now());
        buyRepository.save(buy);
    }


    private List<BookCopy> createBooksByQuantity(List<BookCopy> books, int quantity, Buy buy) {

        /*
            Usa-se o peek ao inves do map pois a intencao nao é transformar o objeto,
            mas sim trabalhar com ele sem mudar o tipo do objeto.
        */

        return books.stream()
                .limit(quantity)
                .peek(book -> {

                    book.setStatus("SOLD");
                    book.setBuy(buy);
                    bookCopyRepository.save(book);
                })
                .collect(Collectors.toList());
    }
}
