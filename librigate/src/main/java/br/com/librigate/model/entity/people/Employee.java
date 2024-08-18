package br.com.librigate.model.entity.people;

import br.com.librigate.model.entity.actions.Restock;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("employee")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cpf")
public class Employee extends Person {

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<Restock> restockList;


    public Employee() {
    }

}
