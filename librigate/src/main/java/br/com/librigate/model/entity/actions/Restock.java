package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.book.FisicalBook;

import java.time.LocalDate;
import java.util.List;

public class Restock {

    private String id;
    private double price;
    private LocalDate restockDate;
    private List<FisicalBook> bookList;

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
