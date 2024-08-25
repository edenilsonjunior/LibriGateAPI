package br.com.librigate.model.repository;

import br.com.librigate.model.entity.book.FisicalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FisicalBookRepository extends JpaRepository<FisicalBook, Long> {

    @Query("SELECT MAX(f.copyNumber) FROM FisicalBook f WHERE f.book.isbn = :isbn")
    Long findMaxCopyNumberByBookIsbn(@Param("isbn") String isbn);

    @Query("SELECT f FROM FisicalBook f WHERE f.book.isbn = :isbn")
    List<FisicalBook> findAllByIsbn(String isbn);

    @Query("SELECT COUNT(f) FROM FisicalBook f WHERE f.book.isbn = :isbn")
    Long countByIsbn(String isbn);
}
