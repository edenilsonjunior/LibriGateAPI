package br.com.librigate.model.mapper.book;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.entity.book.Book;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.mapper.interfaces.IFisicalBookMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FisicalBookMapper implements IFisicalBookMapper {

    @Override
    public FisicalBook toEntity(FisicalBookDTO dto) {
        if (dto == null) {
            return null;
        }

        var book = new Book();
        book.setIsbn(dto.isbn());
        book.setTitle(dto.title());
        book.setDescription(dto.description());
        book.setPublisher(dto.publisher());
        book.setCategory(dto.category());
        book.setAuthorsName(Arrays.stream(dto.authorsName()).toList());
        book.setEdition(dto.edition());
        book.setLaunchDate(dto.launchDate());

        FisicalBook fisicalBook = new FisicalBook();
        fisicalBook.setBook(book);
        fisicalBook.setCopyNumber(dto.copyNumber());
        fisicalBook.setStatus(dto.status());
        fisicalBook.setPrice(dto.price());

        return fisicalBook;
    }

    @Override
    public FisicalBookDTO toDTO(FisicalBook entity) {
        if (entity == null) {
            return null;
        }

        var book = entity.getBook();

        return new FisicalBookDTO(
                entity.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getDescription(),
                book.getPublisher(),
                book.getCategory(),
                book.getAuthorsName().toArray(new String[0]),
                book.getEdition(),
                book.getLaunchDate(),
                entity.getCopyNumber(),
                entity.getStatus(),
                entity.getPrice(),
                entity.getRestock().getId()
        );
    }

}