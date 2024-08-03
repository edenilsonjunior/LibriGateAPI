package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<Buy, Long> {
}
