package findo.lab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import findo.lab.data.model.Employee;
import findo.lab.dto.EmployeeDTO;

@Mapper(componentModel="spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
