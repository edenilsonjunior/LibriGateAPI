package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long>{
}
