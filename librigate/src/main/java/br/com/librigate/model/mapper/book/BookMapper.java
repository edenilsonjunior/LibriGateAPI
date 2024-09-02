package br.com.librigate.model.mapper.book;

import br.com.librigate.dto.book.CreateBookRequest;
import br.com.librigate.dto.book.NewBookRequest;
import br.com.librigate.model.entity.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "reviews", ignore = true)
    Book toEntity(CreateBookRequest dto);

    CreateBookRequest toCreateBookRequest(NewBookRequest request);
}
