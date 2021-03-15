package dev.deyoung.daos;

import dev.deyoung.entities.Employees;

import java.util.Set;

public interface EmployeeDAO {

    //Create
    Employees createEmployee (Employees employee);

    //Read
    Set<Employees> getEmployees();
    Employees getEmployeeByUsername(String username);

}
