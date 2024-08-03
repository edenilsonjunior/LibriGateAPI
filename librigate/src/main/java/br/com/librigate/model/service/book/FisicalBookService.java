package br.com.librigate.model.service.book;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.dto.StockDTO;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.service.interfaces.IFisicalBookService;

import java.util.List;
import java.util.Optional;

public class FisicalBookService implements IFisicalBookService{

    @Override
    public List<StockDTO> getStock() {
        return null;
    }

    @Override
    public StockDTO getStockByBook() {
        return null;
    }

    @Override
    public FisicalBook create(FisicalBookDTO dto) {
        return null;
    }

    @Override
    public FisicalBook update(String id, FisicalBookDTO dto) {
        return null;
    }

    @Override
    public Optional<FisicalBook> findByPK(String id) {
        return Optional.empty();
    }

    @Override
    public List<FisicalBook> findAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
