package com.ominext.cms.web;

import java.util.List;

import com.ominext.cms.exception.RecordNotFoundException;
import com.ominext.cms.model.User;
import com.ominext.cms.service.EmployeeService;
import com.ominext.cms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ominext.cms.model.Employee;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class EmployeeController {
	private final EmployeeService service;
	private final UserService userService;

	public EmployeeController(EmployeeService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}

	@GetMapping("/employees")
	public ModelAndView getAllEmployees() {
		List<Employee> list = service.getAllEmployees();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", user);
		modelAndView.addObject("employees", list);
		modelAndView.setViewName("list-employees");
		return modelAndView;
	}

	@PostMapping("/edit/{id}")
	public ModelAndView editEmployeeById(@PathVariable Long id) throws RecordNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		Employee entity = service.getEmployeeById(id);
		modelAndView.addObject("employee", entity);
		modelAndView.setViewName("add-edit-employee");
		return modelAndView;
	}

	@GetMapping("/redirectCreateEmployee")
	public ModelAndView createEmployee(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("employee", new Employee());
		modelAndView.setViewName("add-edit-employee");
		return modelAndView;
	}

	@PostMapping("/createEmployee")
	public ModelAndView createOrUpdateEmployee(Employee employee) {
		service.createOrUpdateEmployee(employee);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/employees");
		return modelAndView;
	}
	
	@PostMapping("/delete/{id}")
	public ModelAndView deleteEmployeeById(@PathVariable Long id) throws RecordNotFoundException {
		service.deleteEmployeeById(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/employees");
		return modelAndView;
	}
}
