package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BuyFactory {


    public Buy createBuy(BuyRequest request, Customer customer) {

        var entity = new Buy();
        entity.setCustomer(customer);
        entity.setBuyDate(LocalDateTime.now());
        entity.setDueDate(LocalDate.now().plusDays(2));
        entity.setStatus("PENDING");
        entity.setBooks(new ArrayList<>());

        return entity;
    }


    public List<BookCopy> soldBooks(BuyRequest request, Buy buy, Map<String, List<BookCopy>> availableBooks) {

        return request.books()
                .stream()
                .flatMap(buyBook -> {

                    var books = availableBooks.get(buyBook.isbn());
                    return soldBooksByQuantity(books, buyBook.quantity(), buy).stream();
                })
                .toList();
    }

    private List<BookCopy> soldBooksByQuantity(List<BookCopy> books, int quantity, Buy buy) {
        return books.stream()
                .limit(quantity)
                .peek(book -> {

                    book.setStatus("SOLD");
                    book.setBuy(buy);
                })
                .toList();
    }
}
