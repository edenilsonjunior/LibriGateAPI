package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Rent {

    private Long id;
    private List<FisicalBook> bookList;
    private LocalDate rentDate;
    private String status;
    private LocalDate devolutionDate;
    private Optional<LocalDateTime> givenBackAt;

    public Rent(Long id, List<FisicalBook> bookList, LocalDate rentDate, String status, LocalDate devolutionDate, Optional<LocalDateTime> givenBackAt) {
        this.id = id;
        this.bookList = bookList;
        this.rentDate = rentDate;
        this.status = status;
        this.devolutionDate = devolutionDate;
        this.givenBackAt = givenBackAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FisicalBook> getBookList() {
        return bookList;
    }

    public void setBookList(List<FisicalBook> bookList) {
        this.bookList = bookList;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(LocalDate devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public Optional<LocalDateTime> getGivenBackAt() {
        return givenBackAt;
    }

    public void setGivenBackAt(Optional<LocalDateTime> givenBackAt) {
        this.givenBackAt = givenBackAt;
    }
}
