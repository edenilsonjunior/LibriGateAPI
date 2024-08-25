package br.com.librigate.model.mapper.people;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
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
