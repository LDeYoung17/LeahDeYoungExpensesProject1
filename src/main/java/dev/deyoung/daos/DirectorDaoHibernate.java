package dev.deyoung.daos;

import dev.deyoung.entities.Directors;
import dev.deyoung.Utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectorDaoHibernate implements  DirectorDAO{

    private SessionFactory sf = HibernateUtil.createSF();

    @Override
    public Directors createDirector(Directors director) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(director);
            sesh.getTransaction().commit();
            sesh.close();
            return director;
        }catch(HibernateException he){
                he.printStackTrace();
                return null;
            }
    }

    @Override
    public Set<Directors> getDirectors() {
        try{

            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            CriteriaBuilder cb = sesh.getCriteriaBuilder();
            CriteriaQuery<Directors> cq = cb.createQuery(Directors.class);
            Root<Directors> rootEntry = cq.from(Directors.class);
            CriteriaQuery<Directors> all = cq.select(rootEntry);

            TypedQuery<Directors> allQuery = sesh.createQuery(all);
            List<Directors> resultList = allQuery.getResultList();
            Set <Directors> directors = new HashSet<>(resultList);
            return directors;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Directors getDirectorById(int id) {
        try{
            Session sesh = sf.openSession();
            Directors director = sesh.get(Directors.class,id);
            sesh.close();
            return director;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Directors updateDirector(Directors director) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.update(director);
            sesh.getTransaction().commit();
            sesh.close();
            return director;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteDirectorById(int id) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.delete(this.getDirectorById(id));
            sesh.getTransaction().commit();
            sesh.close();
            return true;
        }catch (HibernateException he){
            he.printStackTrace();
            return false;
        }

    }

}
