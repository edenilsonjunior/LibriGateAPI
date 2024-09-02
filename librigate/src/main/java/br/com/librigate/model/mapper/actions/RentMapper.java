package br.com.librigate.model.mapper.actions;

import br.com.librigate.dto.actions.rent.RentRequest;
import br.com.librigate.model.entity.actions.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentMapper {
    RentMapper instance = Mappers.getMapper(RentMapper.class);

    Rent toEntity(RentRequest rentRequest);
}
