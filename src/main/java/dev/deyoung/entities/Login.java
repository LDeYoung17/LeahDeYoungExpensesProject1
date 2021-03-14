package dev.deyoung.entities;

import javax.persistence.*;

@Entity
@Table(name= "login")

public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int userId;

    @Column(name ="username")
    String username;

    @Column(name ="user_password")
    String password;

    @Column(name ="user_role")
    String userRole;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @JoinColumn(name = "employee_id")
    @Column(name ="employee_id")
    int employeeId;

    @JoinColumn(name = "manager_id")
    @Column(name ="manager_id")
    int managerId;

    @Column(name ="director_id")
    @JoinColumn(name = "director_id")
    int directorId;

    public Login() {
    }

    public Login(int userId, String username, String password, String userRole, String firstName, String lastName, int employeeId, int managerId, int directorId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.managerId = managerId;
        this.directorId = directorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUser_id(int userId) {
        this.userId = userId;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
        return "Login{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", managerId='" + managerId + '\'' +
                ", directorId='" + directorId + '\'' +
                '}';
    }
}
