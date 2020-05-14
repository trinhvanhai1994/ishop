package com.ominext.cms.service;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.Employee;
import com.ominext.cms.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees() {
        return (List<Employee>) repository.findAll();
    }

    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);
        if (!employee.isPresent()) {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
        return employee.get();
    }

    public void createOrUpdateEmployee(Employee entity) {
        Optional<Employee> employee = repository.findById(entity.getId());
        if (!employee.isPresent()) {
            repository.save(entity);
        } else {
            Employee newEntity = employee.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());
            repository.save(newEntity);
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = repository.findById(id);
        if (!employee.isPresent()) {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
        repository.deleteById(id);
    }
}