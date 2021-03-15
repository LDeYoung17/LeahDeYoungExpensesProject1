package dev.deyoung.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "managers")

public class Managers {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="manager_id")
    private int managerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "department")
    private String department;

    @JoinColumn(name = "username")
    @Column(name = "username")
    private String username;

    @Column(name = "user_password")
    private String password;



//    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Login> login = new HashSet<Login>();

    @OneToMany(mappedBy = "managerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Employees> employees = new HashSet<Employees>();

    @OneToMany(mappedBy = "managerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Expenses> expenses = new HashSet<Expenses>();


    public Managers() {
    }

    public Managers(int managerId, String firstName, String lastName, String department, String username, String password) {
        this.managerId = managerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.username = username;
        this.password = password;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
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

    public void setLastName(String last_name) {this.lastName = lastName; }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        return "Managers{" +
                "managerId=" + managerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
