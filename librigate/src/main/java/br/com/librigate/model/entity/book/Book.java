package br.com.librigate.model.entity.book;

import br.com.librigate.model.entity.actions.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @NotNull
    @Column(name = "ISBN")
    private String isbn;

    @NotNull
    @Column(name = "Title")
    private String title;

    @NotNull
    @Column(name = "Description")
    private String description;

    @NotNull
    @Column(name = "Publisher")
    private String publisher;

    @NotNull
    @Column(name = "Category")
    private String category;

    @NotNull
    @ElementCollection
    @Column(name = "Authors")
    private List<String> authorsName;

    @NotNull
    @Column(name = "Edition")
    private int edition;

    @NotNull
    @Column(name = "Launch")
    private LocalDate launchDate;

    @OneToMany
    @Column(name = "Reviews")
    private List<Review> reviews;

    public Book() {

    }

    public Book(String isbn, String title, String description, String publisher, String category, List<String> authorsName, int edition, LocalDate launchDate, List<Review> reviews) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.category = category;
        this.authorsName = authorsName;
        this.edition = edition;
        this.launchDate = launchDate;
        this.reviews = reviews;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(List<String> authorsName) {
        this.authorsName = authorsName;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
