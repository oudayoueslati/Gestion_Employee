package com.example.demo.service;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Role;
import com.example.demo.repositories.IEmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceImpl implements IService{

    private final IEmployeeRepository employeeRepository;
    @Autowired
    public ServiceImpl(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateById(Long id, Employee employee) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'employé ne peut pas être null");
        }

        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setNom(employee.getNom());
                    existingEmployee.setPrenom(employee.getPrenom());
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setPassword(employee.getPassword());
                    existingEmployee.setRole(employee.getRole());
                    existingEmployee.setActif(employee.isActif());

                    return employeeRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employé avec ID " + id + " non trouvé"));
    }

    @Override
    public List<Employee> filterEmployees(String nom, String prenom, String email, Role role, Boolean actif) {
        return employeeRepository.filterEmployees(nom, prenom, email, role, actif);
    }

    @Override
    public Page<Employee> getEmployeesPaginated(int page, int size) {
        return employeeRepository.findAll(PageRequest.of(page, size));
    }

}
