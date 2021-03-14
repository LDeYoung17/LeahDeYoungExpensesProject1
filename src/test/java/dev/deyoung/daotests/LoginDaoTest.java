package dev.deyoung.daotests;

import dev.deyoung.daos.*;
import dev.deyoung.entities.Login;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import org.mockito.Mock;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoginDaoTest {

    @Mock
    LoginDAO loginDAO = new LoginDaoHibernate();

    private static Login testLogin = null;

    @Test
    @Order(1)
    void create_user() {

        Login login = new Login(0, "gretchenwieners", "sh0ppIng", "Employee", "Gretchen", "Wieners", 3, 4, 2);
        loginDAO.createLogin(login);
        System.out.println(login);
        Assertions.assertEquals(login.getUsername(), "gretchenwieners");
        testLogin = login;
    }

    @Test
    @Order(2)
    void get_all_users(){

        //int directorId = testDirector.getDirectorId();
        //System.out.println(directorId);
        Set users = new HashSet(loginDAO.getLogins());
        System.out.println(users);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        //Assertions.assertEquals(testDirector.getLastName(), director.getLastName());



    }

    @Test
    @Order(2)
    void get_user_role_by_id() {

        int userId = testLogin.getUserId();
        System.out.println(userId);
        Login login = loginDAO.getLoginById(userId);
        System.out.println(login);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        Assertions.assertEquals(testLogin.getUsername(), login.getUsername());

        System.out.println(testLogin.getUserId() + "'s user role is " + testLogin.getUserRole());

    }


    @Test
    @Order(3)
    void update_user() {

        Login login = loginDAO.getLoginById(testLogin.getUserId());
        login.setUserRole("Manager");

        loginDAO.updateLogin(login);
        Login updatedLogin = loginDAO.getLoginById(login.getUserId());
        Assertions.assertEquals(updatedLogin.getUserRole(), login.getUserRole());
        System.out.println("User ID " + testLogin.getUserId() + "'s role has been updated to " + testLogin.getUserRole());

    }



    @Test
    @Order(4)
    void delete_user() {

        int loginId = testLogin.getUserId();
        System.out.println(loginId);
        boolean deleted = loginDAO.deleteLoginById(loginId);
        System.out.println(deleted);

        Assertions.assertTrue(deleted);


    }

}







