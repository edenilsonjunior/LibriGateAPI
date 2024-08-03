package br.com.librigate.model.entity.people;

import br.com.librigate.model.entity.actions.Restock;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("customer")
public class Employee extends Person {

    private String role;

    @ManyToMany
    @JoinTable(
            name = "employee_restock",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "restock_id")
    )
    private List<Restock> restockList;


    public Employee(){}

    public Employee(String cpf, String firstName, String lastName, LocalDate birthDate, String gender, String telephone, String login, String password, Address address, String role, List<Restock> restockList) {
        super(cpf, firstName, lastName, birthDate, gender, telephone, login, password, address);
        this.role = role;
        this.restockList = restockList;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Restock> getRestockList() {
        return restockList;
    }

    public void setRestockList(List<Restock> restockList) {
        this.restockList = restockList;
    }
}
