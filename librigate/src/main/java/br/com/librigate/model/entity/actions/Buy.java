package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Buy {

    private Long id;
    private List<FisicalBook> books;
    private LocalDateTime buyDate;
    private boolean status;
    private double totalPrice;
    private LocalDate dueDate;
    private Optional<LocalDateTime> paidAt;

    public Buy(Long id, List<FisicalBook> books, LocalDateTime buyDate, boolean status, double totalPrice, LocalDate dueDate, Optional<LocalDateTime> paidAt) {
        this.id = id;
        this.books = books;
        this.buyDate = buyDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dueDate = dueDate;
        this.paidAt = paidAt;
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

    public Optional<LocalDateTime> getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Optional<LocalDateTime> paidAt) {
        this.paidAt = paidAt;
    }
}
