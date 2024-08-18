package br.com.librigate.model.mapper.interfaces;

import br.com.librigate.model.dto.FisicalBookDTO;
import br.com.librigate.model.entity.book.FisicalBook;

public interface IFisicalBookMapper extends IMapper<FisicalBook, FisicalBookDTO> {

    FisicalBook toEntity(FisicalBookDTO dto);

    FisicalBookDTO toDTO(FisicalBook entity);
}
