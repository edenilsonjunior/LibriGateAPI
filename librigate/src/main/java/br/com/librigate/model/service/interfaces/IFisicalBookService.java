package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.entity.book.FisicalBookId;

import java.util.List;
import java.util.Optional;

public interface IFisicalBookService extends IService<FisicalBook, FisicalBookDTO, FisicalBookId> {

    List<StockDTO> getStock();
    StockDTO getStockByBook(String isbn);
}
