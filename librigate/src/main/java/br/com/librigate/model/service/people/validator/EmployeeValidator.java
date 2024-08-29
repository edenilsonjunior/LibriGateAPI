package br.com.librigate.model.service.people.validator;


import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeValidator {

    @Autowired
    private EmployeeRepository employeeRepository;


    public void validateNewEmployee(CreateEmployeeRequest request) {

        validateCPF(request.cpf());
        validateName(request.firstName(), "First name");
        validateName(request.lastName(), "Last name");

        validatePassword(request.password());
        validateBirthDate(request.birthDate());
        validateNotNullOrEmpty(request.role(), "Role");

        validateNotNull(request.telephone(), "Telephone");
        validateNotNull(request.address(), "Address");
    }


    private void validateCPF(String cpf) {
        if (cpf == null || cpf.length() < 11) {
            throw new ValidationException("CPF size must be at least 11 and cannot be null.");
        }

        if(employeeRepository.existsById(cpf)) {
            throw new ValidationException("There is already an register with this cpf.");
        }
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.isEmpty() || name.length() > 50) {
            throw new ValidationException(fieldName + " must be between 1 and 50 characters and cannot be null.");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null || !birthDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Birth date must be in the past and cannot be null.");
        }
    }

    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " cannot be null.");
        }
    }

    private void validateNotNullOrEmpty(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new ValidationException(fieldName + " cannot be null or empty.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long.");
        }
    }
}
