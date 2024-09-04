package br.com.librigate.model.service.book.factory;

import br.com.librigate.dto.book.UpdateBookRequest;
import br.com.librigate.model.entity.book.Book;
import org.springframework.stereotype.Component;

@Component
public class BookFactory {

    public void update(UpdateBookRequest request, Book entity) {

        request.title().ifPresent(entity::setTitle);
        request.description().ifPresent(entity::setDescription);
        request.publisher().ifPresent(entity::setPublisher);
        request.category().ifPresent(entity::setCategory);
        request.authorsName().ifPresent(entity::setAuthorsName);
        request.edition().ifPresent(entity::setEdition);
        request.launchDate().ifPresent(entity::setLaunchDate);
    }
}
