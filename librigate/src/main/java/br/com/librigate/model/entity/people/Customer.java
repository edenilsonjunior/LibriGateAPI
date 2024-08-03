package br.com.librigate.model.entity.people;

import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("customer")
public class Customer extends Person {

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "active", nullable = false)
    private boolean active;


    @ManyToMany
    @JoinTable(
            name = "purchases",
            joinColumns = @JoinColumn(name = "customer_cpf"),
            inverseJoinColumns = @JoinColumn(name = "buy_id")
    )
    private List<Buy> purchases;


    @ManyToMany
    @JoinTable(
            name = "rents",
            joinColumns = @JoinColumn(name = "customer_cpf"),
            inverseJoinColumns = @JoinColumn(name = "rent_id")
    )
    private List<Rent> rentList;

    public Customer() {}

    public Customer(String cpf, String firstName, String lastName, LocalDate birthDate, String gender, String telephone, String login, String password, Address address, LocalDate registrationDate, boolean active, List<Buy> purchases, List<Rent> rentList) {
        super(cpf, firstName, lastName, birthDate, gender, telephone, login, password, address);

        this.registrationDate = registrationDate;
        this.active = active;
        this.purchases = purchases;
        this.rentList = rentList;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Buy> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Buy> purchases) {
        this.purchases = purchases;
    }

    public List<Rent> getRentList() {
        return rentList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }
}
