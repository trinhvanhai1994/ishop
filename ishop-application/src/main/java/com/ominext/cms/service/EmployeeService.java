package com.ominext.cms.service;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.EmployeeEntity;
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

    public List<EmployeeEntity> getAllEmployees() {
        return (List<EmployeeEntity>) repository.findAll();
    }

    public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<EmployeeEntity> employee = repository.findById(id);
        if (!employee.isPresent()) {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
        return employee.get();
    }

    public void createOrUpdateEmployee(EmployeeEntity entity) {
        Optional<EmployeeEntity> employee = repository.findById(entity.getId());
        if (!employee.isPresent()) {
            repository.save(entity);
        } else {
            EmployeeEntity newEntity = employee.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());
            repository.save(newEntity);
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Optional<EmployeeEntity> employee = repository.findById(id);
        if (!employee.isPresent()) {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
        repository.deleteById(id);
    }
}