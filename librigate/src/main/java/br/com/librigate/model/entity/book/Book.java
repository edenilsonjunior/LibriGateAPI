package br.com.librigate.model.entity.book;

import br.com.librigate.model.entity.actions.Review;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "book")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "isbn")
public class Book {

    @Id
    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "category", nullable = false)
    private String category;

    @ElementCollection
    @Column(name = "authors", nullable = false)
    private List<String> authorsName;

    @Column(name = "edition", nullable = false)
    private int edition;

    @Column(name = "launch_date", nullable = false)
    private LocalDate launchDate;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;


    public Book() {
    }

}
