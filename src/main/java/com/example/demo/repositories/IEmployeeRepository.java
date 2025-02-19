package com.example.demo.repositories;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAll();
    @Query("SELECT e FROM Employee e WHERE " +
            "(:nom IS NULL OR e.nom LIKE %:nom%) AND " +
            "(:prenom IS NULL OR e.prenom LIKE %:prenom%) AND " +
            "(:email IS NULL OR e.email LIKE %:email%) AND " +
            "(:role IS NULL OR e.role = :role) AND " +
            "(:actif IS NULL OR e.actif = :actif)")
    List<Employee> filterEmployees(
            @Param("nom") String nom,
            @Param("prenom") String prenom,
            @Param("email") String email,
            @Param("role") Role role,
            @Param("actif") Boolean actif
    );
}
