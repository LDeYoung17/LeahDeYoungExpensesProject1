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

    @JoinColumn(name = "username")
    @Column(name ="username")
    private String username;

    @Column(name ="user_password")
    private String password;

//    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Login> login = new HashSet<Login>();

    @OneToMany(mappedBy = "employeeId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Expenses> expenses = new HashSet<Expenses>();

    public Employees() {
    }

    public Employees(int employeeId, String firstName, String lastName, String department, int managerId, String username, String password) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.managerId = managerId;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", managerId=" + managerId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
