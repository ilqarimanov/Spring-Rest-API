package az.spring.rest.demo.springrestdemo.service.impl;

import az.spring.rest.demo.springrestdemo.enums.ErrorCodeEnum;
import az.spring.rest.demo.springrestdemo.exception.CustomRestException;
import az.spring.rest.demo.springrestdemo.model.Employee;
import az.spring.rest.demo.springrestdemo.repository.EmployeeRepository;
import az.spring.rest.demo.springrestdemo.rest.model.dto.EmployeeDto;
import az.spring.rest.demo.springrestdemo.rest.model.response.EmployeeResponse;
import az.spring.rest.demo.springrestdemo.service.EmployeeService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse getAllEmployees() {
        List<EmployeeDto> employeeDtosList = employeeRepository.findAll().stream().map(employee -> convertToDto(employee))
                .collect(Collectors.toList());

        return makeEmployeeResponse(employeeDtosList);
    }

    @Override
    public EmployeeDto getEmployee(long id) {
       return employeeRepository.findById(id)
                .map(employee -> convertToDto(employee))
                .orElseThrow(()->new CustomRestException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND));
    }

    @Override
    public EmployeeResponse getEmployeeByNameAndSurname(String name, String surname) {
        List<EmployeeDto>employees =   employeeRepository.findByNameAndSurname(name,surname).stream().map(employee -> convertToDto(employee))
                .collect(Collectors.toList());
        return makeEmployeeResponse(employees);
    }

    @Override
    public void insert(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto,employee);
        employeeRepository.save(employee);
    }



    @Override
    public void uptade(EmployeeDto employeeDto, long id) {
        Employee employee = getEmployeeById(id);
        if(employeeDto.getName()!=null)
            employee.setName(employeeDto.getName());

        if(employeeDto.getSurname()!=null)
            employee.setSurname(employee.getSurname());
        if(employeeDto.getAge()>0)
            employee.setAge(employee.getAge());
        if(employeeDto.getSalary()>0)
            employee.setSalary(employee.getSalary());
         employeeRepository.save(employee);
    }

    @Override
    public void uptadeSome(EmployeeDto employeeDto, long id) {
        Employee employee = getEmployeeById(id);
        employee.setName(employeeDto.getName());
        employee.setSurname(employee.getSurname());
        employee.setAge(employee.getAge());
        employee.setSalary(employee.getSalary());
        employeeRepository.save(employee);
    }

    @Override
    public void delete(long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);


    }

    private Employee getEmployeeById(long id ){
return  employeeRepository.findById(id).orElseThrow(()->new CustomRestException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND));
    }
    private EmployeeDto convertToDto(Employee employee) {
       EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee,employeeDto);
        return employeeDto;
    }
    private EmployeeResponse makeEmployeeResponse(List<EmployeeDto>employees){
       return EmployeeResponse.builder()
                .employees(employees)
                .build();

    }
}
