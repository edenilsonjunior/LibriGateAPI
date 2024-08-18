package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.actions.Review;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.interfaces.IBookMapper;
import br.com.librigate.model.repository.BookRepository;
import br.com.librigate.model.service.interfaces.IBookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private IBookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        var entityList = bookRepository.findAll();

        return entityList.stream()
                .filter(b -> b.getCategory().equals(category))
                .toList();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        var entityList = bookRepository.findAll();

        return entityList.stream()
                .filter(b -> b.getAuthorsName().equals(author))
                .toList();
    }

    @Override
    public List<Review> getReview(String bookIsbn) {
        var entityList = bookRepository.findAll();

        return entityList.stream()
                .filter(b -> b.getIsbn().equals(bookIsbn))
                .findFirst().get().getReviews();
    }

    @Override
    public Book create(BookDTO dto) {
        var entity = bookMapper.toEntity(dto);
        return bookRepository.save(entity);
    }

    @Override
    public Book update(BookDTO bookDTO) {
        var entity = bookRepository.findById(bookDTO.isbn()).
                orElseThrow(() -> new EntityNotFoundException("Book not found"));

        var entityUpdated = bookMapper.toEntity(bookDTO);

        entity.setTitle(entityUpdated.getTitle());
        entity.setDescription(entityUpdated.getDescription());
        entity.setPublisher(entityUpdated.getPublisher());
        entity.setCategory(entityUpdated.getCategory());
        entity.setAuthorsName(entityUpdated.getAuthorsName());
        entity.setEdition(entityUpdated.getEdition());
        entity.setLaunchDate(entityUpdated.getLaunchDate());

        return bookRepository.save(entity);
    }

    @Override
    public Optional<Book> findByPK(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(String id) {
        var entity = bookRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Book not found"));

        bookRepository.delete(entity);
    }
}
