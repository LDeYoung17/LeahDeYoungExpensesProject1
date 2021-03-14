package dev.deyoung.daos;

import dev.deyoung.entities.Directors;

import java.util.Set;

public interface DirectorDAO {

    //Create
    Directors createDirector (Directors director);

    //Read
    Set<Directors> getDirectors();
    Directors getDirectorById(int id);

    //Update
    Directors updateDirector(Directors director);

    //Delete
    boolean deleteDirectorById(int id);

}
