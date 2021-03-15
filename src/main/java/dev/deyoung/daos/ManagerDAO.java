package dev.deyoung.daos;

import dev.deyoung.entities.Employees;
import dev.deyoung.entities.Managers;

import java.util.Set;

public interface ManagerDAO {

    //Create
    Managers createManager (Managers manager);

    //Read
    Set<Managers> getManagers();
//    Managers getManagerById(int id);
    Managers getManagerByUsername(String username);


    //Update
//    Managers updateManager(Managers manager);

    //Delete
//    boolean deleteManagerById(int id);

}
