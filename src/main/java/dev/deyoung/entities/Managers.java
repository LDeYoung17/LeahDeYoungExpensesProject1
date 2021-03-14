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

    @Column(name ="director_id")
    @JoinColumn(name = "director_id")
    private int directorId;

//    @OneToMany(mappedBy = "managerId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Employees> employees = new HashSet<Employees>();
//
//    @OneToMany(mappedBy = "managerId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Login> login = new HashSet<Login>();
//
//    @OneToMany(mappedBy = "managerId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Expenses> expenses = new HashSet<Expenses>();


    public Managers() {
    }

    public Managers(int managerId, String firstName, String lastName, String department, int directorId) {
        this.managerId = managerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.directorId = directorId;
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

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    @Override
    public String toString() {
        return "Managers{" +
                "managerId=" + managerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", directorId='" + directorId + '\'' +
                '}';
    }
}
