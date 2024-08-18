package br.com.librigate.model.mapper.interfaces;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.entity.book.Book;

public interface IBookMapper extends IMapper<Book, BookDTO> {

    Book toEntity(BookDTO dto);

    BookDTO toDTO(Book entity);
}
