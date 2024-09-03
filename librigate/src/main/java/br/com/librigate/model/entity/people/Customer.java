package br.com.librigate.model.entity.people;

import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Rent;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cpf")
public class Customer extends Person {

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "customer")
    private List<Buy> purchases;

    @OneToMany(mappedBy = "customer")
    private List<Rent> rentList;


    public Customer() {
    }

}
