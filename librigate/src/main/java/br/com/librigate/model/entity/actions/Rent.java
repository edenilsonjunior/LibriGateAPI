package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinTable(
            name = "book_List",
            joinColumns = @JoinColumn(name = "rent_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<FisicalBook> bookList;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column( nullable = false)
    private String status;

    @Column(name = "devolution_date", nullable = false)
    private LocalDate devolutionDate;

    @Column(name = "given_back_at", nullable = true)
    private LocalDateTime givenBackAt;

    public Rent() {
    }

    public Rent(Long id, List<FisicalBook> bookList, LocalDate rentDate, String status, LocalDate devolutionDate, LocalDateTime givenBackAt) {
        this.id = id;
        this.bookList = bookList;
        this.rentDate = rentDate;
        this.status = status;
        this.devolutionDate = devolutionDate;
        this.givenBackAt = givenBackAt;
    }

    public Rent(Long id, List<FisicalBook> bookList, LocalDate rentDate, String status, LocalDate devolutionDate) {
        this.id = id;
        this.bookList = bookList;
        this.rentDate = rentDate;
        this.status = status;
        this.devolutionDate = devolutionDate;
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

    public LocalDateTime getGivenBackAt() {
        return givenBackAt;
    }

    public void setGivenBackAt(LocalDateTime givenBackAt) {
        this.givenBackAt = givenBackAt;
    }
}
