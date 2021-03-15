package dev.deyoung.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "login")

public class Login {

    @Id
    @Column(name ="username")
    String username;

    @Column(name ="user_password")
    String password;


    @OneToMany(mappedBy = "username", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employees> employees = new HashSet<Employees>();

    @OneToMany(mappedBy = "username", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Managers> managers = new HashSet<Managers>();


    public Login() {
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
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
        return "Login{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
