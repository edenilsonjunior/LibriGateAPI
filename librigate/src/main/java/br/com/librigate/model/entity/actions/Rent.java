package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "status" ,nullable = false)
    private String status;

    @Column(name = "devolution_date", nullable = false)
    private LocalDate devolutionDate;

    @Column(name = "given_back_at", nullable = true)
    private LocalDateTime givenBackAt;

    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    @JsonBackReference
    private Customer customer;

    @NotNull
    @ManyToMany
    @JoinTable(name = "rent_book",
            joinColumns = @JoinColumn(name = "rent_id"),
            inverseJoinColumns = {
                @JoinColumn(name = "book_copy_id", referencedColumnName = "id")
            }
    )
    private List<BookCopy> bookList;


    public Rent() { }

}
