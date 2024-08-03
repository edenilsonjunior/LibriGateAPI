package br.com.librigate.model.repository;

import br.com.librigate.model.entity.book.FisicalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FisicalBookRepository extends JpaRepository<FisicalBook, String> {
}
