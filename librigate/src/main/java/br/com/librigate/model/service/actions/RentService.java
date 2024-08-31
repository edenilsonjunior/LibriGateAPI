package br.com.librigate.model.service.actions;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.dto.actions.rent.RentResponse;
import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.model.entity.actions.Rent;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Customer;
import br.com.librigate.model.repository.FisicalBookRepository;
import br.com.librigate.model.repository.CustomerRepository;
import br.com.librigate.model.repository.RentRepository;
import br.com.librigate.model.service.HandleRequest;
import br.com.librigate.model.service.actions.factory.RentFactory;
import br.com.librigate.model.service.actions.validator.RentValidator;
import br.com.librigate.model.service.interfaces.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentService implements IRentService {

    private final RentRepository rentRepository;
    private final CustomerRepository customerRepository;
    private final FisicalBookRepository fisicalBookRepository; // Usando FisicalBookRepository
    private final RentFactory rentFactory;
    private final RentValidator rentValidator;

    @Autowired
    public RentService(RentRepository rentRepository, CustomerRepository customerRepository,
                       FisicalBookRepository fisicalBookRepository, RentFactory rentFactory, RentValidator rentValidator) {
        this.rentRepository = rentRepository;
        this.customerRepository = customerRepository;
        this.fisicalBookRepository = fisicalBookRepository;
        this.rentFactory = rentFactory;
        this.rentValidator = rentValidator;
    }

    @Transactional
    @Override
    public RentResponse rent(RentRequest request) {
        return HandleRequest.handle(() -> {
            var customer = findCustomerByCPF(request.customerCpf());

            var availableBooks = getAvailableBooks(request);  // Buscar livros físicos disponíveis

            var entity = rentFactory.createRent(request, customer);

            entity.setRentDate(LocalDate.now());
            entity.setDevolutionDate(LocalDate.now().plusWeeks(1));
            entity.setStatus("RENTED");

            var rentedBooks = rentFactory.rentedBooks(entity, availableBooks);

            entity.setBookList(rentedBooks);
            rentRepository.save(entity);

            return toRentResponse(entity);
        });
    }

    @Transactional
    @Override
    public RentResponse processDevolutionBook(Long rentId) {
        return HandleRequest.handle(() -> {
            var entity = findRentById(rentId);

            rentValidator.validateDevolution(entity);

            entity.setGivenBackAt(LocalDateTime.now());
            entity.setStatus("RETURNED");
            rentRepository.save(entity);

            restoreBooks(entity.getBookList());

            return toRentResponse(entity);
        });
    }

    @Transactional
    public RentResponse renewRent(Long rentId) {
        return HandleRequest.handle(() -> {
            var entity = findRentById(rentId);

            rentValidator.validateRenewal(entity);

            entity.setDevolutionDate(entity.getDevolutionDate().plusWeeks(1));
            rentRepository.save(entity);

            return toRentResponse(entity);
        });
    }

    @Override
    public List<Rent> getRents(String cpf) {
        return rentRepository.findByCustomerCpf(cpf);
    }

    @Override
    public Rent getRendById(Long rentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRendById'");
    }

}
