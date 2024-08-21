package br.com.librigate.model.mapper.interfaces;

import br.com.librigate.model.dto.customer.CreateCustomerRequest;
import br.com.librigate.model.dto.customer.UpdateCustomerRequest;
import br.com.librigate.model.entity.people.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper instance = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "login", source = "cpf")
    Customer toEntity(CreateCustomerRequest createRequest);
}
