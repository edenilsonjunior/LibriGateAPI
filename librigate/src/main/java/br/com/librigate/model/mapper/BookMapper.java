package br.com.librigate.model.mapper;

import br.com.librigate.model.dto.employee.book.NewBookRequest;
import br.com.librigate.model.entity.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toEntity(NewBookRequest request);
}
