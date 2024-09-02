package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.dto.actions.rent.RentResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.RentRepository;
import br.com.librigate.model.service.actions.factory.RentFactory;
import br.com.librigate.model.service.actions.validator.RentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FisicalBookRepository fisicalBookRepository;

    @Autowired
    private RentFactory rentFactory;

    @Autowired
    private RentValidator rentValidator;

    public RentResponse rent(RentRequest request) {
        rentValidator.validateRent(request);

        var customer = customerRepository.findById(request.customerCpf())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        var availableBooks = rentFactory.getAvailableBooksForRent(request.booksIsbn());
        var rent = rentFactory.createRent(customer);
        rentFactory.associateBooksToRent(availableBooks, rent);

        return toRentResponse(rent);
    }

    public RentResponse processDevolutionBook(Long rentId) {
        rentValidator.validateDevolution(rentId);

        var rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));

        rent.setStatus("RETURNED");
        rent.setGivenBackAt(LocalDate.now());

        restoreBooks(rent);

        return toRentResponse(rent);
    }

    public RentResponse renewRent(Long rentId) {
        rentValidator.validateRenew(rentId);

        var rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));

        rent.setDevolutionDate(rent.getDevolutionDate().plusWeeks(1));
        return toRentResponse(rent);
    }

    private void restoreBooks(Rent rent) {
        rent.getBookList().forEach(book -> {
            book.setStatus("AVAILABLE");
            book.setRent(null);
            fisicalBookRepository.save(book);
        });
    }

    public List<Rent> getRents(String cpf) {
        return rentRepository.findByCustomerCpf(cpf);
    }

    public Rent getRentById(Long rentId) {
        return rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("Rent not found"));
    }

    private RentResponse toRentResponse(Rent rent) {
        return new RentResponse(
                rent.getId(),
                rent.getCustomer().getCpf(),
                rent.getRentDate(),
                rent.getStatus(),
                rent.getDevolutionDate(),
                rent.getGivenBackAt()
        );
    }
}
