package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.buy.BuyRequest;
import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BuyFactory {

    private final BuyRepository buyRepository;
    private final FisicalBookRepository fisicalBookRepository;

    @Autowired
    public BuyFactory(BuyRepository buyRepository, FisicalBookRepository fisicalBookRepository) {
        this.buyRepository = buyRepository;
        this.fisicalBookRepository = fisicalBookRepository;
    }


    public Buy createBuy(BuyRequest request, Customer customer) {

        var entity = new Buy();
        entity.setCustomer(customer);
        entity.setBuyDate(LocalDateTime.now());
        entity.setDueDate(LocalDate.now().plusDays(2));
        entity.setStatus("PENDING");

        return buyRepository.save(entity);
    }


    public BuyResponse createBuyResponse(Buy entity) {
        return new BuyResponse(
                entity.getId(),
                entity.getCustomer().getCpf(),
                entity.getTotalPrice(),
                entity.getBuyDate(),
                entity.getDueDate(),
                Optional.ofNullable(entity.getPaidAt()),
                entity.getStatus()
        );
    }


    public List<FisicalBook> soldBooks(BuyRequest request, Buy buy, Map<String, List<FisicalBook>> availableBooks) {

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


    private List<FisicalBook> createBooksByQuantity(List<FisicalBook> books, int quantity, Buy buy) {

        return books.stream()
                .limit(quantity)
                .peek(book -> {

                    book.setStatus("SOLD");
                    book.setBuy(buy);
                    fisicalBookRepository.save(book);
                })
                .collect(Collectors.toList());
    }

}
