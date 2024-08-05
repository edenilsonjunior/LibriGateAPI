package br.com.librigate.model.entity.people;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {

    @Id
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    public Person() {
    }

}
