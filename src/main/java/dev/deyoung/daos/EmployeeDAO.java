package dev.deyoung.daos;

import dev.deyoung.entities.Employees;

import java.util.Set;

public interface EmployeeDAO {

    //Create
    Employees createEmployee (Employees employee);

    //Read
    Set<Employees> getEmployees();
    Employees getEmployeeById(int id);

    //Update
    Employees updateEmployee(Employees employee);

    //Delete
    boolean deleteEmployeeById(int id);

}
