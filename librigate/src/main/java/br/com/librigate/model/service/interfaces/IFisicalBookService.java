package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.entity.book.FisicalBook;

import java.util.List;

public interface IFisicalBookService extends IService<FisicalBook, FisicalBookDTO, String> {

    List<StockDTO> getStock();
    StockDTO getStockByBook();
}
