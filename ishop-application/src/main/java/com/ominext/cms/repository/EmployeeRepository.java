package com.ominext.cms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ominext.cms.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
