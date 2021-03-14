package dev.deyoung.daotests;


import dev.deyoung.daos.ManagerDAO;
import dev.deyoung.daos.ManagerDaoHibernate;
import dev.deyoung.entities.Directors;
import dev.deyoung.entities.Managers;
import org.junit.jupiter.api.*;
import java.util.HashSet;
import java.util.Set;
import org.mockito.Mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ManagerDaoTest {


    @Mock
    ManagerDAO managerDAO = new ManagerDaoHibernate();

    private static Managers testManager = null;
    private static Directors testDirector = null;

    //Create test

    @Test
    @Order(1)
    void create_manager(){

        Managers JanisIan = new Managers(0, "Janis", "Ian", "The Coolest People You'll Ever Meet", 2);
        managerDAO.createManager(JanisIan);
        System.out.println(JanisIan);
        Assertions.assertEquals(JanisIan.getLastName(), "Ian");
        testManager = JanisIan;
    }

    @Test
    @Order(2)
    void get_all_managers(){

        //int directorId = testDirector.getDirectorId();
        //System.out.println(directorId);
        Set managers = new HashSet(managerDAO.getManagers());
        System.out.println(managers);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        //Assertions.assertEquals(testDirector.getLastName(), director.getLastName());



    }

    @Test
    @Order(3)
    void get_director_by_id(){

        int managerId = testManager.getManagerId();
        System.out.println(managerId);
        Managers manager = managerDAO.getManagerById(managerId);
        System.out.println(manager);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        Assertions.assertEquals(testManager.getLastName(), manager.getLastName());

        System.out.println(testManager.getLastName() + "'s ID number is " + testManager.getManagerId());


    }



    @Test
    @Order(4)
    void update_manager(){

        Managers manager = managerDAO.getManagerById(testManager.getManagerId());
        manager.setLastName("G");

        managerDAO.updateManager(manager);
        Managers updatedManager = managerDAO.getManagerById(manager.getManagerId());
        Assertions.assertEquals(updatedManager.getManagerId(), manager.getManagerId());
        System.out.println("Manager ID " + testManager.getManagerId() + "'s last name has been updated to " + testManager.getLastName());


    }

    @Test
    @Order(5)
    void update_manager_director(){

        Managers manager = managerDAO.getManagerById(testManager.getManagerId());

        manager.setDirectorId(3);
        managerDAO.updateManager(manager);
        Managers updatedManager = managerDAO.getManagerById( manager.getManagerId());


        Assertions.assertEquals(updatedManager.getDirectorId(), manager.getDirectorId());

        System.out.println("Client ID " + testManager.getManagerId() + "'s director has been updated to " + testManager.getDirectorId());

    }


//    @Test
//    @Order(6)
//    void delete_manager(){
//
//        int managerId = testManager.getManager_id();
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
