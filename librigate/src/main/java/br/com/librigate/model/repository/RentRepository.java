package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long>{

    @Query("SELECT f FROM Rent f WHERE f.customer.cpf = :cpf")
    List<Rent> findAllByCustomerCpf(String cpf);
}
