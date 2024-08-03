package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
}
