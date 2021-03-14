package dev.deyoung.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "directors")

public class Directors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="director_id")
    private int directorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "division")
    private String division;

//    @OneToMany(mappedBy = "directorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Managers> managers = new HashSet<Managers>();

//    @OneToMany(mappedBy = "directorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Employees> employees = new HashSet<Employees>();
//
//    @OneToMany(mappedBy = "directorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Login> login = new HashSet<Login>();
//
//    @OneToMany(mappedBy = "directorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Expenses> expenses = new HashSet<Expenses>();


    public Directors() {
    }

    public Directors(int directorId, String firstName, String lastName, String division) {
        this.directorId = directorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.division = division;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "Directors{" +
                "directorId=" + directorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", division='" + division + '\'' +
                '}';
    }

}


