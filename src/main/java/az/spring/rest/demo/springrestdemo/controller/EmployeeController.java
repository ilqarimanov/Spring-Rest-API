package az.spring.rest.demo.springrestdemo.controller;


import az.spring.rest.demo.springrestdemo.model.Employee;
import az.spring.rest.demo.springrestdemo.rest.model.dto.EmployeeDto;
import az.spring.rest.demo.springrestdemo.rest.model.response.EmployeeResponse;
import az.spring.rest.demo.springrestdemo.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping
    public EmployeeResponse getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employee_id}")
    public EmployeeDto getEmployee(@PathVariable("employee_id") long id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/search")
    public EmployeeResponse getEmployeeByNameAndSurname(@RequestParam("name") String name, @RequestParam("surname") String surname) {
        return employeeService.getEmployeeByNameAndSurname(name, surname);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody EmployeeDto employeeDto){
        employeeService.insert(employeeDto);
    }

    @PutMapping("/{id}")
    public void uptadeAll(@RequestBody EmployeeDto employeeDto,@PathVariable("id") long id ){
    employeeService.uptade(employeeDto,id);
    }
    @PatchMapping ("/{id}")
    public void uptadeSome(@RequestBody EmployeeDto employeeDto,@PathVariable("id") long id ){
        employeeService.uptadeSome(employeeDto,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id){
        employeeService.delete(id);
    }
}
