package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    Optional<Buy> findByCustomerCpfAndId(String cpf, Long id);
    List<Buy> findByCustomerCpf(String cpf);
}
