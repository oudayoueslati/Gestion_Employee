package com.example.demo.controllers;
import com.example.demo.entities.Employee;
import com.example.demo.entities.Role;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final IService employeeService;
    @Autowired
    public EmployeeController(IService service) {
        this.employeeService = service;
    }

    @PostMapping("/addEmployee")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/AllEmployee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    @GetMapping("/GetEmployee/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @DeleteMapping("/DeleteEmployee/{id}")
    public void deleteOffer(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @PutMapping("/UpdateEmployee/{id}")
    public Employee updateById(@PathVariable Long id,@RequestBody Employee employee) {
        return employeeService.updateById(id, employee);
    }
    @GetMapping("/filter")
    public List<Employee> filterEmployees(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean actif) {

       return  employeeService.filterEmployees(nom, prenom, email, role, actif);

    }
    @GetMapping("/paginated")
    public Page<Employee> getEmployeesPaginated(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {
        return employeeService.getEmployeesPaginated(page, size);
    }
}
