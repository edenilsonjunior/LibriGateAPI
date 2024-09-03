package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.people.Customer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "review")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_isbn", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Book book;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating;


    public Review() {
    }

}
