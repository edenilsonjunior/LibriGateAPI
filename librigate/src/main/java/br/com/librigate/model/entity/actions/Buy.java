package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.BookCopy;
import br.com.librigate.model.entity.people.Customer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "buy")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buy_date", nullable = false)
    private LocalDateTime buyDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "paid_at", nullable = true)
    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    @JsonIdentityReference(alwaysAsId = true)
    private Customer customer;

    @OneToMany(mappedBy = "buy")
     
    private List<BookCopy> books;


    @PrePersist
    public void calculateTotalPrice() {

        totalPrice = books.stream()
                          .mapToDouble(BookCopy::getPrice)
                          .sum();
    }

    public Buy() {
    }

    @Override
    public String toString() {
        return "Buy + " + id;
    }

}
