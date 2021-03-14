package dev.deyoung.servicetests;

import dev.deyoung.daos.ExpensesDaoHibernate;
import dev.deyoung.daos.LoginDaoHibernate;
import dev.deyoung.entities.Expenses;
import dev.deyoung.entities.Login;
import dev.deyoung.services.LoginService;
import dev.deyoung.services.LoginServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoginServiceTest {
    private static Login testLogin = null;
    @Mock
    LoginService loginService = new LoginServiceImpl(new LoginDaoHibernate());

    @Test
    @Order(1)

    void create_login_in_service(){
        Login login = new Login(0, "gretchenwieners", "toasterstrudel", "Employee", "Gretchen", "Wieners", 3, 4, 2);
        loginService.newLogin(login);

        System.out.println(login);
        System.out.println(login.getUsername());

        //Assertions.assertNotEquals(0, expense.getExpenseId());
        Assertions.assertNotEquals(null, login.getUsername());
        testLogin = login;

    }

    @Test
    @Order(2)

    void get_all_logins_in_service(){

        Set<Login> loginSet = new HashSet<Login>(loginService.getAllLogins());
        Set <Login> testLoginSet = new HashSet<Login>();
        testLoginSet.add(testLogin);

        Assertions.assertEquals(testLoginSet.size(), loginSet.size());

    }

    @Test
    @Order(3)

    void get_login_user_by_id(){
        Login login = testLogin;
        Login loginIdTime = loginService.getLoginById(login.getUserId());

        Assertions.assertEquals(loginIdTime.getUserId(), testLogin.getUserId());

    }

    @Test
    @Order(4)

    void get_login_by_username(){
        Login login = testLogin;
        Set<Login> loginByUsername = new HashSet<>(loginService.getLoginByUsername(login.getUsername()));
        System.out.println(loginByUsername);
        System.out.println(login);

        Assertions.assertFalse(loginByUsername.isEmpty());


    }

    @Test
    @Order(5)

    void get_login_by_user_role(){
        Login login = testLogin;
        Set<Login> loginByUserRole = new HashSet<>(loginService.getLoginByUserRole(login.getUserRole()));
        System.out.println(login);
        System.out.println(loginByUserRole);

        Assertions.assertFalse(loginByUserRole.isEmpty());


    }

    @Test
    @Order(6)

    void get_login_by_employee_id(){
        Login login = testLogin;
        Set<Login> loginByEmployeeId = new HashSet<>(loginService.getLoginByEmployeeId(login.getEmployeeId()));
        System.out.println(login);
        System.out.println(loginByEmployeeId);

        Assertions.assertFalse(loginByEmployeeId.isEmpty());


    }

    @Test
    @Order(7)

    void get_login_by_manager_id(){
        Login login = testLogin;
        Set<Login> loginByManagerId = new HashSet<>(loginService.getLoginByManagerId(login.getManagerId()));
        System.out.println(login);
        System.out.println(loginByManagerId);

        Assertions.assertFalse(loginByManagerId.isEmpty());


    }

    @Test
    @Order(8)

    void get_login_by_director_id(){
        Login login = testLogin;
        Set<Login> loginByDirectorId = new HashSet<>(loginService.getLoginByDirectorId(login.getDirectorId()));
        System.out.println(login);
        System.out.println(loginByDirectorId);

        Assertions.assertFalse(loginByDirectorId.isEmpty());


    }

    @Test
    @Order(9)

    void update_password(){

        Login login = testLogin;
        Login updatedLogin = loginService.updatePassword(login, "thatIsSoFetch!");

        System.out.println(updatedLogin.getPassword());
        System.out.println(updatedLogin);

        Assertions.assertNotEquals("toasterstrudel", updatedLogin.getPassword());

    }

    @Test
    @Order(10)
    void update_user_role(){

        Login login = testLogin;
        Login updatedLogin = loginService.updateUserRole(login, "Manager");

        System.out.println(updatedLogin.getUserRole());
        System.out.println(updatedLogin);

        Assertions.assertNotEquals("Employee", updatedLogin.getUserRole());

    }

    @Test
    @Order(11)
    void update_first_name(){

        Login login = testLogin;
        Login updatedLogin = loginService.updateFirstName(login, "Karen");

        System.out.println(updatedLogin.getFirstName());
        System.out.println(updatedLogin);

        Assertions.assertNotEquals("Gretchen", updatedLogin.getFirstName());

    }

    @Test
    @Order(12)
    void update_last_name(){

        Login login = testLogin;
        Login updatedLogin = loginService.updateLastName(login, "Smith");

        System.out.println(updatedLogin.getLastName());
        System.out.println(updatedLogin);

        Assertions.assertNotEquals("Wieners", updatedLogin.getLastName());

    }

    @Test
    @Order(13)

    void delete_login(){

        Login login = testLogin;
        boolean deleted = loginService.deleteLoginById(login.getUserId());

        System.out.println(login.getUserId());

        Assertions.assertTrue(deleted);



    }
}
