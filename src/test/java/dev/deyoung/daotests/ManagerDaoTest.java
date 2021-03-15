package dev.deyoung.daotests;


import dev.deyoung.daos.ManagerDAO;
import dev.deyoung.daos.ManagerDaoHibernate;
import dev.deyoung.entities.Managers;
import org.junit.jupiter.api.*;
import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ManagerDaoTest {


    private static ManagerDAO managerDAO = new ManagerDaoHibernate();

    private static Managers testManager = null;

    @Test
    @Order(1)
    void create_manager(){

        Managers JanisIan = new Managers(0, "Kevin", "G", "Mathletes", "keving", "mathletes");
        managerDAO.createManager(JanisIan);
        System.out.println(JanisIan);
        Assertions.assertEquals(JanisIan.getLastName(), "G");
        testManager = JanisIan;
        System.out.println(testManager);
    }

    @Test
    @Order(2)
    void get_all_managers(){

        Set managers = new HashSet(managerDAO.getManagers());
        System.out.println(managers);

    }


    @Test
    @Order(3)
    void get_manager_by_username(){

        String username = testManager.getUsername();
        System.out.println(username);
        Managers manager = managerDAO.getManagerByUsername(username);
        Assertions.assertEquals(manager.getUsername(), testManager.getUsername());


    }

}
