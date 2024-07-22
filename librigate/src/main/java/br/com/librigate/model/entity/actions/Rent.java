package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Rent {

    private String id;
    private List<FisicalBook> bookList;
    private LocalDate rentDate;
    private boolean status;
    private LocalDate devolutionDate;
    private Optional<LocalDateTime> givenBackAt;

    public Rent(String id, List<FisicalBook> bookList, LocalDate rentDate, boolean status, LocalDate devolutionDate, Optional<LocalDateTime> givenBackAt) {
        this.id = id;
        this.bookList = bookList;
        this.rentDate = rentDate;
        this.status = status;
        this.devolutionDate = devolutionDate;
        this.givenBackAt = givenBackAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
