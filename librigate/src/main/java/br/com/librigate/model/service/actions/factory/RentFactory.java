package br.com.librigate.model.service.actions.factory;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class RentFactory {

    public Rent createRent(RentRequest request, Customer customer) {
        Rent rent = new Rent();
        rent.setCustomer(customer);
        rent.setRentDate(LocalDate.now());
        rent.setStatus("RENTED");
        rent.setDevolutionDate(LocalDate.now().plusWeeks(1));
        return rent;
    }

    public List<FisicalBook> rentedBooks(Rent rent, Map<String, List<FisicalBook>> availableBooks) {
        return availableBooks.values().stream()
                .flatMap(List::stream)
                .peek(book -> {
                    book.setStatus("RENTED");
                    book.setRent(rent);
                })
                .toList();
    }
}
