package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Restock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestockRepository extends JpaRepository<Restock, Long> {
}
