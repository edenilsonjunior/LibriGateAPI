package br.com.librigate.model.entity.book;

import br.com.librigate.model.entity.actions.Review;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fisical_book")
public class FisicalBook extends Book {

    @Id
    @Column(name = "Copy")
    private int copyNumber;

    @NotNull
    @Column(name = "Status")
    private String status;

    @NotNull
    @Column(name = "Price")
    private double price;

    public FisicalBook(){
        super();
    }

    public FisicalBook(String isbn, String title, String description, String publisher, String category, List<String> authorsName, int edition, LocalDate launchDate, List<Review> reviews, int copyNumber, String status, double price) {
        super(isbn, title, description, publisher, category, authorsName, edition, launchDate, reviews);
        this.copyNumber = copyNumber;
        this.status = status;
        this.price = price;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

