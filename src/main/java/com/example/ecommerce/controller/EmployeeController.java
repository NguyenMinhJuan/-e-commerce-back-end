package com.example.ecommerce.controller;

import com.example.ecommerce.model.Employee;
import com.example.ecommerce.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> listEmployee = (List<Employee>) employeeService.findAll();
        if (listEmployee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listEmployee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> createEmployee(@PathVariable String id, @RequestBody Employee employee) {
        Optional<Employee> Epl = employeeService.findById(id);
        if (!Epl.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employeeService.save(employee);
        return new ResponseEntity<>(employee,HttpStatus.OK);

    }

}
