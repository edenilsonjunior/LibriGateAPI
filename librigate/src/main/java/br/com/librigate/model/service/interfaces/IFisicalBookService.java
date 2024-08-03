package br.com.librigate.model.service.interfaces;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.service.interfaces.IService;

import java.util.List;

public interface IFisicalBookService extends IService<FisicalBook, FisicalBookDTO, String> {

    public List<StockDTO> getStock();
    public StockDTO getStockByBook();
}
