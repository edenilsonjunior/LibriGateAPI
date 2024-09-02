package br.com.librigate.model.mapper.people;

import br.com.librigate.dto.people.employee.CreateEmployeeRequest;
import br.com.librigate.model.entity.people.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "login", source = "cpf")
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "restockList", ignore = true)
    Employee toEntity(CreateEmployeeRequest request);
}