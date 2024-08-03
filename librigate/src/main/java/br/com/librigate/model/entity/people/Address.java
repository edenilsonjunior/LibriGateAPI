package br.com.librigate.model.entity.people;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String zipCode;

    @NotNull
    private String street;

    @NotNull
    private String distric;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private int number;

    @Null
    private String complement;

    public Address(){}

    public Address(Long id, String zipCode, String street, String distric, String city, String state, int number, String complement) {
        this.id = id;
        this.zipCode = zipCode;
        this.street = street;
        this.distric = distric;
        this.city = city;
        this.state = state;
        this.number = number;
        this.complement = complement;
    }


    public @NotNull Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public @NotNull String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public  String getCity() {
        return city;
    }

    public void setCity( String city) {
        this.city = city;
    }

    public  String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
