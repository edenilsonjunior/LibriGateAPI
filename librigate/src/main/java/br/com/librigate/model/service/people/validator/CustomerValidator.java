package br.com.librigate.model.service.people.validator;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomerValidator {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateNewCustomer(CreateCustomerRequest request){
        validateCustomerExists(request.cpf());
        validateCpf(request.cpf());
        validateName(request.firstName(), "First name");
        validateName(request.lastName(), "Last name");

        validatePassword(request.password());
        validateGender(request.gender());
        validateBirthDate(request.birthDate());

        validateNotNull(request.telephone(), "Telephone");
        validateNotNull(request.address(), "Address");
    }

    private void validateCpf(String cpf){
        validate(cpf == null || cpf.length() != 11, "CPF size must be at least 11 and cannot be null.");
    }

    private void validateName(String name, String fieldName) {
        validate(name == null || name.isEmpty() || name.length() > 50, fieldName + " must be between 1 and 50 characters and cannot be null.");
    }

    private void validateGender(String gender){
        validate(!gender.equalsIgnoreCase("M")
                && !gender.equalsIgnoreCase("F"), "Invalid Gender.");
    }

    private void validateCustomerExists(String cpf){
        validate(customerRepository.existsById(cpf), "There is already an register with this cpf.");
    }

    private void validateBirthDate(LocalDate birthDate){
        validate(birthDate == null || birthDate.isAfter(LocalDate.now()), "Invalid birth date.");
    }

    private void validateNotNull(Object value, String fieldName) {
        validate(value == null, fieldName + " cannot be null.");
    }

    private void validatePassword(String password) {
        validate(password == null || password.length() < 8,"Password must be at least 8 characters long.");
    }

    private void validate(boolean condition, String message) {
        if (condition) {
            throw new ValidationException(message);
        }
    }
}
