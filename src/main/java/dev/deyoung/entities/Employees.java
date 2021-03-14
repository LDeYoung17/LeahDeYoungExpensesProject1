package dev.deyoung.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "employees")

public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="employee_id")
    private int employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "department")
    private String department;

    @JoinColumn(name = "manager_id")
    @Column(name ="manager_id")
    private int managerId;

    @Column(name ="director_id")
    private int directorId;

    @OneToMany(mappedBy = "employeeId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Login> login = new HashSet<Login>();

    @OneToMany(mappedBy = "employeeId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Expenses> expenses = new HashSet<Expenses>();

    public Employees() {
    }

    public Employees(int employeeId, String firstName, String lastName, String department, int managerId, int directorId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.managerId = managerId;
        this.directorId = directorId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", managerId=" + managerId +
                ", directorId=" + directorId +
                '}';
    }
}
