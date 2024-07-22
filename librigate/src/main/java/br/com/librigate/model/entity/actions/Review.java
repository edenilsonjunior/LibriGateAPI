package br.com.librigate.model.entity.actions;

import br.com.librigate.model.entity.people.Customer;

public class Review {

    private Customer customer;
    private String description;
    private double rating;

    public Review(Customer customer, String description, double rating) {
        this.customer = customer;
        this.description = description;
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
