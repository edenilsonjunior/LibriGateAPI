package br.com.librigate.model.entity.book;

import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Restock;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fisical_book")
public class FisicalBook {

    @EmbeddedId
    private FisicalBookId bookDetails;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "restock_id", nullable = false)
    @JsonBackReference
    private Restock restock;

    @ManyToOne
    @JoinColumn(name = "buy_id", nullable = true)
    @JsonBackReference
    private Buy buy;

    public FisicalBook() {
    }

}
