package dev.deyoung.services;

import dev.deyoung.daos.LoginDAO;
import dev.deyoung.entities.Login;
import java.util.HashSet;
import java.util.Set;

public class LoginServiceImpl implements LoginService{

    private LoginDAO loginDAO;
    public LoginServiceImpl(LoginDAO loginDAO) {this.loginDAO = loginDAO;}

    @Override
    public Login newLogin(Login login) {
        this.loginDAO.createLogin(login);
        return login;
    }

    @Override
    public Set<Login> getAllLogins() {
        return this.loginDAO.getLogins();
    }

    @Override
    public Login getLoginById(int id) {
        return this.loginDAO.getLoginById(id);
    }

    @Override
    public Set<Login> getLoginByUsername(String username) {
        Set<Login> allLogins = this.loginDAO.getLogins();
        Set<Login> loginByUsername = new HashSet<Login>();

        for(Login login : allLogins) {
            if (login.getUsername().contains(username)) {
                loginByUsername.add(login);
            }

        }
        return loginByUsername;
    }

    @Override
    public Set<Login> getLoginByUserRole(String userRole) {
        Set<Login> allLogins = this.loginDAO.getLogins();
        Set<Login> loginByUserRole = new HashSet<Login>();

        for(Login login : allLogins) {
            if (login.getUserRole().contains(userRole)) {
                loginByUserRole.add(login);
            }

        }
        return loginByUserRole;
    }

    @Override
    public Set<Login> getLoginByEmployeeId(int employeeId) {
        Set<Login> allLogins = this.loginDAO.getLogins();
        Set<Login> loginByEmployeeId = new HashSet<Login>();

        for(Login login : allLogins) {
            if (login.getEmployeeId() == employeeId){
                loginByEmployeeId.add(login);
            }

        }
        return loginByEmployeeId;
    }

    @Override
    public Set<Login> getLoginByManagerId(int managerId) {
        Set<Login> allLogins = this.loginDAO.getLogins();
        Set<Login> loginByManagerId = new HashSet<Login>();

        for(Login login : allLogins) {
            if (login.getManagerId() == managerId){
                loginByManagerId.add(login);
            }

        }
        return loginByManagerId;
    }

//    @Override
//    public Set<Login> getLoginByDirectorId(int directorId) {
//        Set<Login> allLogins = this.loginDAO.getLogins();
//        Set<Login> loginByDirectorId = new HashSet<Login>();
//
//        for(Login login : allLogins) {
//            if (login.getDirectorId() == directorId){
//                loginByDirectorId.add(login);
//            }
//
//        }
//        return loginByDirectorId;    }

    @Override
    public Login updatePassword(Login login, String password) {
        login.setPassword(password);
        this.loginDAO.updateLogin(login);

        return login;
    }

    @Override
    public Login updateUserRole(Login login, String userRole) {
        login.setUserRole(userRole);
        this.loginDAO.updateLogin(login);

        return login;
    }

    @Override
    public Login updateFirstName(Login login, String firstName) {
        login.setFirstName(firstName);
        this.loginDAO.updateLogin(login);

        return login;
    }

    @Override
    public Login updateLastName(Login login, String lastName) {
        login.setLastName(lastName);
        this.loginDAO.updateLogin(login);

        return login;
    }

    @Override
    public boolean deleteLoginById(int loginId) {
        return this.loginDAO.deleteLoginById(loginId);
    }

}
