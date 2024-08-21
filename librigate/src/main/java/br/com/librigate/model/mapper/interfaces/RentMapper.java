package br.com.librigate.model.mapper.interfaces;

import br.com.librigate.model.dto.customer.rent.RentRequest;
import br.com.librigate.model.entity.actions.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentMapper {
    RentMapper instance = Mappers.getMapper(RentMapper.class);

    Rent toEntity(RentRequest rentRequest);
}
