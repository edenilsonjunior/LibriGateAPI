package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restock")
public class Restock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "restock_date", nullable = false)
    private LocalDate restockDate;

    @OneToMany
    @JoinTable(
            name = "book_List",
            joinColumns = @JoinColumn(name = "restock_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<FisicalBook> bookList;

    public Restock() {
    }

    public Restock(String id, double price, LocalDate restockDate, List<FisicalBook> bookList) {
        this.id = id;
        this.price = price;
        this.restockDate = restockDate;
        this.bookList = bookList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getRestockDate() {
        return restockDate;
    }

    public void setRestockDate(LocalDate restockDate) {
        this.restockDate = restockDate;
    }

    public List<FisicalBook> getBookList() {
        return bookList;
    }

    public void setBookList(List<FisicalBook> bookList) {
        this.bookList = bookList;
    }
}
