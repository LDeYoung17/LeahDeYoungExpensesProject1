package dev.deyoung.daotests;

import dev.deyoung.daos.DirectorDAO;
import dev.deyoung.daos.DirectorDaoHibernate;
import dev.deyoung.entities.Directors;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import org.mockito.Mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class DirectorDaoTest {

    @Mock
    DirectorDAO directorDAO = new DirectorDaoHibernate();
    private static Directors testDirector = null;

    //Create test

    @Test
    @Order(1)
    void create_director(){

        Directors CadyHeron = new Directors(1,"Cady","Heron","Mathletes");
        directorDAO.createDirector(CadyHeron);
        System.out.println(CadyHeron);
        Assertions.assertEquals(CadyHeron.getLastName(), "Heron");
        testDirector = CadyHeron;
    }

    @Test
    @Order(2)
    void get_all_directors(){

        //int directorId = testDirector.getDirectorId();
        //System.out.println(directorId);
        Set directors = new HashSet(directorDAO.getDirectors());
        System.out.println(directors);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        //Assertions.assertEquals(testDirector.getLastName(), director.getLastName());



    }


    @Test
    @Order(3)
    void get_director_by_id(){

        int directorId = testDirector.getDirectorId();
        System.out.println(directorId);
        Directors director = directorDAO.getDirectorById(directorId);
        System.out.println(director);
        //Assertions.assertEquals(testClient.getClientName(), "John Smith");
        Assertions.assertEquals(testDirector.getLastName(), director.getLastName());

        System.out.println(testDirector.getLastName() + "'s ID number is " + testDirector.getDirectorId());


    }





    @Test
    @Order(4)
    void update_director(){

        Directors director = directorDAO.getDirectorById(testDirector.getDirectorId());
        director.setDivision("Spring Fling Queen");

        directorDAO.updateDirector(director);
        Directors updatedDirector = directorDAO.getDirectorById(director.getDirectorId());
        Assertions.assertEquals(updatedDirector.getDirectorId(), director.getDirectorId());
        System.out.println("Director ID " + testDirector.getDirectorId() + "'s division has been updated to " + testDirector.getDivision());


    }


    @Test
    @Order(5)
    void delete_director(){

        int directorId = testDirector.getDirectorId();
        System.out.println(directorId);
        boolean deleted = directorDAO.deleteDirectorById(directorId);
        System.out.println(deleted);

        Assertions.assertTrue(deleted);




    }





}
