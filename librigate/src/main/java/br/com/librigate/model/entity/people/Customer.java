package br.com.librigate.model.entity.people;

import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("customer")
public class Customer extends Person {

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Buy> purchases;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Rent> rentList;


    public Customer() {
    }

}
