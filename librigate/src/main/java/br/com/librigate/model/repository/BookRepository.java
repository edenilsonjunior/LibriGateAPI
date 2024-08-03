package br.com.librigate.model.repository;

import br.com.librigate.model.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
