package com.example.demo;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Role;
import com.example.demo.repositories.IEmployeeRepository;
import com.example.demo.service.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoApplicationTests {

	@Mock
	private IEmployeeRepository employeeRepository;

	@InjectMocks
	private ServiceImpl employeeService;

	private Employee emp1, emp2;

	@BeforeEach
	void setUp() {
		emp1 = new Employee(1L, "Alice", "Smith", "alice@example.com", "pass", true, Role.ADMINISTRATEUR);
		emp2 = new Employee(2L, "Bob", "Johnson", "bob@example.com", "pass", false, Role.INGENIEUR);
	}

	@Test
	void testAddEmployee() {
		when(employeeRepository.save(emp1)).thenReturn(emp1);

		Employee savedEmployee = employeeService.addEmployee(emp1);

		assertNotNull(savedEmployee);
		assertEquals("Alice", savedEmployee.getPrenom());
		verify(employeeRepository, times(1)).save(emp1);
	}

	@Test
	void testGetAllEmployees() {
		List<Employee> employees = Arrays.asList(emp1, emp2);
		when(employeeRepository.findAll()).thenReturn(employees);

		List<Employee> result = employeeService.getAllEmployees();

		assertEquals(2, result.size());
		verify(employeeRepository, times(1)).findAll();
	}

	@Test
	void testFindById() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1));

		Employee foundEmployee = employeeService.findById(1L);

		assertNotNull(foundEmployee);
		assertEquals("Alice", foundEmployee.getPrenom());
		verify(employeeRepository, times(1)).findById(1L);
	}

	@Test
	void testDeleteById() {
		doNothing().when(employeeRepository).deleteById(1L);

		employeeService.deleteById(1L);

		verify(employeeRepository, times(1)).deleteById(1L);
	}

	@Test
	void testFilterEmployees() {
		List<Employee> employees = Arrays.asList(emp1);
		when(employeeRepository.filterEmployees("Alice", null, null, null, null)).thenReturn(employees);

		List<Employee> result = employeeService.filterEmployees("Alice", null, null, null, null);

		assertEquals(1, result.size());
		assertEquals("Alice", result.get(0).getPrenom());
		verify(employeeRepository, times(1)).filterEmployees("Alice", null, null, null, null);
	}

	@Test
	void testGetEmployeesPaginated() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Employee> employeePage = new PageImpl<>(Arrays.asList(emp1, emp2));

		when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

		Page<Employee> result = employeeService.getEmployeesPaginated(0, 2);

		assertEquals(2, result.getContent().size());
		verify(employeeRepository, times(1)).findAll(pageable);
	}
	@Test
	void testUpdateById() {
		Employee updatedEmployee = new Employee(1L, "Alice", "Johnson", "alice.johnson@example.com", "newpass", true, Role.INGENIEUR);

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1));
		when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

		Employee result = employeeService.updateById(1L, updatedEmployee);

		assertNotNull(result);
		assertEquals("Alice", result.getPrenom());
		assertEquals("Johnson", result.getNom());
		assertEquals("alice.johnson@example.com", result.getEmail());
		assertEquals("newpass", result.getPassword());
		assertEquals(Role.INGENIEUR, result.getRole());
		assertTrue(result.isActif());

		verify(employeeRepository, times(1)).findById(1L);
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}
}
