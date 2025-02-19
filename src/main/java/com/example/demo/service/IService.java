package com.example.demo.service;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IService {

    Employee addEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee findById(Long id);
    void deleteById(Long id);
    Employee updateById(Long id, Employee employee);
    List<Employee> filterEmployees(String nom, String prenom, String email, Role role, Boolean actif);
    Page<Employee> getEmployeesPaginated(int page, int size);
}
