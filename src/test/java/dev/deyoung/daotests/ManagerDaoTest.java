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

        Managers JanisIan = new Managers(0, "Janis", "Ian", "The Coolest People You'll Ever Meet", "gretchenwieners", "helloThere");
        managerDAO.createManager(JanisIan);
        System.out.println(JanisIan);
        Assertions.assertEquals(JanisIan.getLastName(), "Ian");
        testManager = JanisIan;
        System.out.println(testManager);
    }

    @Test
    @Order(2)
    void get_all_managers(){

        Set managers = new HashSet(managerDAO.getManagers());
        System.out.println(managers);

    }

//    @Test
//    @Order(3)
//    void get_manager_by_id(){
//
//        int managerId = testManager.getManagerId();
//        System.out.println(managerId);
//        Managers manager = managerDAO.getManagerById(managerId);
//        Assertions.assertEquals(manager.getUsername(), testManager.getUsername());
//
//
//    }

    @Test
    @Order(4)
    void get_manager_by_username(){

        String username = testManager.getUsername();
        System.out.println(username);
        Managers manager = managerDAO.getManagerByUsername(username);
        Assertions.assertEquals(manager.getUsername(), testManager.getUsername());


    }



//    @Test
//    @Order(5)
//    void update_manager(){
//
//        Managers manager = managerDAO.getManagerById(testManager.getManagerId());
//        manager.setLastName("G");
//
//        managerDAO.updateManager(manager);
//        Managers updatedManager = managerDAO.getManagerById(manager.getManagerId());
//        Assertions.assertEquals(updatedManager.getManagerId(), manager.getManagerId());
//        System.out.println("Manager ID " + testManager.getManagerId() + "'s last name has been updated to " + testManager.getLastName());
//
//
//    }




//    @Test
//    @Order(6)
//    void delete_manager(){
//
//        int managerId = testManager.getManagerId();
//        System.out.println(managerId);
//        boolean deleted = managerDAO.deleteManagerById(managerId);
//        System.out.println(deleted);
//
//        Assertions.assertTrue(deleted);
//
//
//
//
//    }





}
