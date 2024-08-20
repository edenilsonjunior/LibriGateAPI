package br.com.librigate.model.mapper.book;

import br.com.librigate.model.dto.BookDTO;
import br.com.librigate.model.dto.book.CreateBookRequest;
import br.com.librigate.model.entity.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toEntity(CreateBookRequest dto);

    BookDTO toDTO(Book entity);
}
