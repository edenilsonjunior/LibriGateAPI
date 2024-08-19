package br.com.librigate.model.mapper;

import br.com.librigate.model.dto.employee.EmployeeRequest;
import br.com.librigate.model.entity.people.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "login", source = "cpf")
    Employee toEntity(EmployeeRequest request);

}