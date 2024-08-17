package br.com.librigate.model.mapper.book;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.mapper.interfaces.IBookMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BookMapper implements IBookMapper {

    @Override
    public Book toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }

        Book book = new Book();
        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setDescription(dto.description());
        book.setPublisher(dto.publisher());
        book.setCategory(dto.category());
        book.setAuthorsName(Arrays.stream(dto.authorsName()).toList());
        book.setEdition(dto.edition());
        book.setLaunchDate(dto.launchDate());

        return book;
    }

    @Override
    public BookDTO toDTO(Book entity) {
        if (entity == null) {
            return null;
        }

        return new BookDTO(
                entity.getIsbn(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPublisher(),
                entity.getCategory(),
                entity.getAuthorsName().toArray(new String[0]),
                entity.getEdition(),
                entity.getLaunchDate()
        );
    }

}
