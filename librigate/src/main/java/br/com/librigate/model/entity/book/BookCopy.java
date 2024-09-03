package br.com.librigate.model.entity.book;

import br.com.librigate.model.entity.actions.Buy;
import br.com.librigate.model.entity.actions.Restock;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book_copy")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "isbn")
    @JsonIdentityReference(alwaysAsId = true)
    private Book book;

    @Column(name = "copy_number", nullable = false)
    private Long copyNumber;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "restock_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Restock restock;

    @ManyToOne
    @JoinColumn(name = "buy_id", nullable = true)
    @JsonIdentityReference(alwaysAsId = true)
    private Buy buy;

    public BookCopy() {
    }

}
