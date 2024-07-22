package br.com.librigate.model.entity.people;

import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;

import java.time.LocalDate;
import java.util.List;

public class Customer extends Person {

    private LocalDate registrationDate;
    private boolean active;
    private List<Buy> purchases;
    private List<Rent> rentList;

    public Customer(String cpf, String firstName, String lastName, LocalDate birthDate, String gender,
                    String telephone, String login, String password, Address address, LocalDate registrationDate,
                    boolean active, List<Buy> purchases, List<Rent> rentList) {
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
