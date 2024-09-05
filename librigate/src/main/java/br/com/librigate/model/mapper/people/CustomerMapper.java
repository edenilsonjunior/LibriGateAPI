package br.com.librigate.model.mapper.people;

import br.com.librigate.dto.people.customer.CreateCustomerRequest;
import br.com.librigate.dto.people.customer.CustomerResponse;
import br.com.librigate.model.entity.people.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "login", source = "cpf")
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "rentList", ignore = true)
    @Mapping(target = "address", ignore = true)
    Customer toEntity(CreateCustomerRequest createRequest);

    CustomerResponse toResponse(Customer customer);
}
