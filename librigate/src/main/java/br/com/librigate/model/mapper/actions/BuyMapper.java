package br.com.librigate.model.mapper.actions;

import br.com.librigate.dto.actions.buy.BuyResponse;
import br.com.librigate.model.entity.actions.Buy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface BuyMapper {

    BuyMapper INSTANCE = Mappers.getMapper(BuyMapper.class);


    @Mapping(source = "customer.cpf", target = "customerCpf")
    @Mapping(source = "paidAt", target = "paidAt", qualifiedByName = "optionalPaidAt")
    BuyResponse toBuyResponse(Buy buy);

    @Named("optionalPaidAt")
    default Optional<LocalDateTime> optionalPaidAt(LocalDateTime paidAt) {
        return Optional.ofNullable(paidAt);
    }

}
