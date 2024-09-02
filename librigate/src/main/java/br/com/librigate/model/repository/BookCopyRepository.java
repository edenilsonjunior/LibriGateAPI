package br.com.librigate.model.repository;

import br.com.librigate.model.entity.book.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    @Query("SELECT MAX(f.copyNumber) FROM BookCopy f WHERE f.book.isbn = :isbn")
    Long findMaxCopyNumberByBookIsbn(@Param("isbn") String isbn);

    @Query("SELECT f FROM BookCopy f WHERE f.book.isbn = :isbn AND f.status = 'AVAILABLE'")
    List<BookCopy> findAllAvailableByIsbn(String isbn);

    @Query("SELECT COUNT(f) FROM BookCopy f WHERE f.book.isbn = :isbn")
    Long countByIsbn(String isbn);

    @Query("SELECT COUNT(f) > 0 FROM BookCopy f WHERE f.book.isbn = :isbn")
    boolean existsByIsbn(String isbn);

}
