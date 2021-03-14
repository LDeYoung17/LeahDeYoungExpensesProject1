package dev.deyoung.daos;

import dev.deyoung.entities.Login;

import java.util.Set;

public interface LoginDAO {

    //Create
    Login createLogin (Login login);

    //Read
    Set<Login> getLogins();
    Login getLoginById(int id);

    //Update
    Login updateLogin(Login login);

    //Delete
    boolean deleteLoginById(int id);

}
