package com.demo.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.data.Employee;
import com.demo.data.EmployeeRepository;
import com.github.javafaker.Faker;


@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository repository;

	@RequestMapping(method = GET, value = "/")
	public String index(@PageableDefault(size = 10) Pageable pageable,
            Model model) {
		
		Page<Employee> page = repository.findAll(pageable);
		model.addAttribute("page", page);

		return "index";
	}
	
	@RequestMapping(method = GET, value = "/init")
	public String init() {
		repository.deleteAll();
		for(int i = 0; i < 100; i++) {
			final Employee employee = new Employee();
			employee.setName(Faker.instance().name().fullName());
			employee.setDept(Faker.instance().company().profession());
			employee.setSalary(Faker.instance().number().numberBetween(20000, 100000));
			repository.save(employee);
		}
		return "redirect:/";
	}

	@RequestMapping(method = GET, value = "/download")
	public ModelAndView download(final Model model) {
		
		final Iterable<Employee> employees = repository.findAll();
		return new ModelAndView(new ExcelView(), "employees", employees);
	}
}
