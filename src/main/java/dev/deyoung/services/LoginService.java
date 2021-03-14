package dev.deyoung.services;

import dev.deyoung.entities.Login;
import java.util.Set;

public interface LoginService {

    //Create
    Login newLogin (Login login);

    //Read
    Set<Login> getAllLogins();
    Login getLoginById(int id);
    Set<Login> getLoginByUsername(String username);
    Set<Login> getLoginByUserRole(String userRole);
    Set<Login> getLoginByEmployeeId(int employeeId);
    Set<Login> getLoginByManagerId(int managerId);
//    Set<Login> getLoginByDirectorId(int directorId);

    //Update
    Login updatePassword(Login login, String password);
    Login updateUserRole(Login login, String userRole);
    Login updateFirstName(Login login, String firstName);
    Login updateLastName(Login login, String lastName);

    //Delete
    boolean deleteLoginById(int loginId);
}
