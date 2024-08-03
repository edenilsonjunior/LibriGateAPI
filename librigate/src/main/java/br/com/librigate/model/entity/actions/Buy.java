package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "buy")
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinTable(
            name = "buy_books",
            joinColumns = @JoinColumn(name = "buy_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<FisicalBook> books;

    @Column(name = "buy_date", nullable = false)
    private LocalDateTime buyDate;

    @Column(nullable = false)
    private boolean status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "paid_at", nullable = true)
    private LocalDateTime paidAt;

    public Buy() {
    }

    public Buy(Long id, List<FisicalBook> books, LocalDateTime buyDate, boolean status, double totalPrice, LocalDate dueDate, LocalDateTime paidAt) {
        this.id = id;
        this.books = books;
        this.buyDate = buyDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dueDate = dueDate;
        this.paidAt = paidAt;
    }

    public Buy(Long id, List<FisicalBook> books, LocalDateTime buyDate, boolean status, double totalPrice, LocalDate dueDate) {
        this.id = id;
        this.books = books;
        this.buyDate = buyDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FisicalBook> getBooks() {
        return books;
    }

    public void setBooks(List<FisicalBook> books) {
        this.books = books;
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
