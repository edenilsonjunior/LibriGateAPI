package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Employee;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "restock")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Restock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "restock_date", nullable = false)
    private LocalDate restockDate;

    @ManyToOne
    @JoinColumn(name = "employee_cpf")
    @JsonIdentityReference(alwaysAsId = true)
    private Employee employee;

    @OneToMany(mappedBy = "restock")
    private List<BookCopy> bookList;

    public Restock() {
    }

}
