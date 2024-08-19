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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @Column(name = "copy_number", nullable = false)
    private Long copyNumber;

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
