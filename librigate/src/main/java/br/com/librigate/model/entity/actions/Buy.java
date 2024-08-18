package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.people.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "buy")
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buy_date", nullable = false)
    private LocalDateTime buyDate;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "paid_at", nullable = true)
    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "customer_cpf")
    private Customer customer;

    @OneToMany(mappedBy = "buy")
    private List<FisicalBook> books;

    @PrePersist
    private void calculateTotalPrice() {

        totalPrice = books.stream()
                          .mapToDouble(FisicalBook::getPrice)
                          .sum();
    }

    public Buy() {
    }

}
