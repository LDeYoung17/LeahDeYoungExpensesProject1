package dev.deyoung.daotests;

import dev.deyoung.daos.*;
import dev.deyoung.entities.Employees;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class EmployeeDaoTest {

    private static EmployeeDAO employeeDAO = new EmployeeDaoHibernate();
    private static Employees testEmployee = null;

    @Test
    @Order(1)
    void create_employee() {

        Employees employee = new Employees(0, "John", "Smith", "Mathletes", 3, "johnsmith", "lovesmathletestoo");
        employeeDAO.createEmployee(employee);
        System.out.println(employee);
        Assertions.assertEquals(employee.getLastName(), "Smith");
        testEmployee = employee;
    }

    @Test
    @Order(2)
    void get_all_employees(){

        Set employees = new HashSet(employeeDAO.getEmployees());
        System.out.println(employees);

    }

        @Test
        @Order(3)
        void get_employee_by_username() {

            String username = testEmployee.getUsername();

            System.out.println(username);
            Employees employee = employeeDAO.getEmployeeByUsername(username);
            System.out.println(employee);
            //Assertions.assertEquals(testClient.getClientName(), "John Smith");
            Assertions.assertEquals(testEmployee.getLastName(), employee.getLastName());

            System.out.println(testEmployee.getLastName() + "'s ID number is " + testEmployee.getEmployeeId());

        }

}







