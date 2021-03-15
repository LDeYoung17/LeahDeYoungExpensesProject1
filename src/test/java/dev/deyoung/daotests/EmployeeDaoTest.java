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

        Employees GretchenWieners = new Employees(0, "Karen", "Smith II", "The Coolest People You'll Ever Meet", 3, "karensmith", "imamouseduh");
        employeeDAO.createEmployee(GretchenWieners);
        System.out.println(GretchenWieners);
        Assertions.assertEquals(GretchenWieners.getLastName(), "Smith II");
        testEmployee = GretchenWieners;
    }

    @Test
    @Order(2)
    void get_all_employees(){

        //int directorId = testDirector.getDirectorId();
        //System.out.println(directorId);
        Set employees = new HashSet(employeeDAO.getEmployees());
        System.out.println(employees);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        //Assertions.assertEquals(testDirector.getLastName(), director.getLastName());



    }

//        @Test
//        @Order(3)
//        void get_employee_by_id() {
//
//            int employeeId = testEmployee.getEmployeeId();
//            System.out.println(employeeId);
//            Employees employee = employeeDAO.getEmployeeById(employeeId);
//            System.out.println(employee);
//            //Assertions.assertEquals(testClient.getClientName(), "John Smith");
//            Assertions.assertEquals(testEmployee.getLastName(), employee.getLastName());
//
//            System.out.println(testEmployee.getLastName() + "'s ID number is " + testEmployee.getEmployeeId());
//
//        }

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

//
//        @Test
//        @Order(4)
//        void update_employee() {
//
//            Employees employee = employeeDAO.getEmployeeById(testEmployee.getEmployeeId());
//            employee.setDepartment("The Plastics");
//
//            employeeDAO.updateEmployee(employee);
//            Employees updatedEmployee = employeeDAO.getEmployeeById(employee.getEmployeeId());
//            Assertions.assertEquals(updatedEmployee.getDepartment(), employee.getDepartment());
//            System.out.println("Employee ID " + testEmployee.getEmployeeId() + "'s department has been updated to " + testEmployee.getDepartment());
//
//        }
//
//        @Test
//        @Order(5)
//        void update_employee_manager() {
//
//            Employees employee = employeeDAO.getEmployeeById(testEmployee.getEmployeeId());
//
//            employee.setManagerId(22);
//            employeeDAO.updateEmployee(employee);
//            Employees updatedEmployee = employeeDAO.getEmployeeById(employee.getEmployeeId());
//
//
//            Assertions.assertEquals(updatedEmployee.getManagerId(), employee.getManagerId());
//
//            System.out.println("Employee ID " + testEmployee.getEmployeeId() + "'s manager has been updated to " + testEmployee.getManagerId());
//
//        }


//        @Test
//        @Order(6)
//        void delete_employee() {
//
//            int employeeId = testEmployee.getEmployeeId();
//            System.out.println(employeeId);
//            boolean deleted = employeeDAO.deleteEmployeeById(employeeId);
//            System.out.println(deleted);
//
//            Assertions.assertTrue(deleted);
//
//
//        }
//
}







