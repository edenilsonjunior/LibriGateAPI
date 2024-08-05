package br.com.librigate.model.entity.book;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class FisicalBookId implements Serializable {

    private Long copyNumber;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    public FisicalBookId() {
    }

    public FisicalBookId(Long copyNumber, Book book) {
        this.copyNumber = copyNumber;
        this.book = book;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FisicalBookId that = (FisicalBookId) o;
        return Objects.equals(copyNumber, that.copyNumber) &&
                Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(copyNumber, book);
    }

}
