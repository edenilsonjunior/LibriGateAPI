package br.com.librigate.model.mapper.interfaces;

import br.com.librigate.model.dto.customer.buy.BuyRequest;
import br.com.librigate.model.entity.actions.Buy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyMapper {

    BuyMapper instance = Mappers.getMapper(BuyMapper.class);

    Buy toEntity(BuyRequest buyRequest);
}
