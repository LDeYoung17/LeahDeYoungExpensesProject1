package dev.deyoung.daos;

import dev.deyoung.entities.Directors;
import dev.deyoung.entities.Managers;
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

public class ManagerDaoHibernate implements  ManagerDAO{

    private SessionFactory sf = HibernateUtil.createSF();

    @Override
    public Managers createManager(Managers manager) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(manager);
            sesh.getTransaction().commit();
            sesh.close();
            return manager;
        }catch(HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
   public Set<Managers> getManagers() {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            CriteriaBuilder cb = sesh.getCriteriaBuilder();
            CriteriaQuery<Managers> cq = cb.createQuery(Managers.class);
            Root<Managers> rootEntry = cq.from(Managers.class);
            CriteriaQuery<Managers> all = cq.select(rootEntry);

            TypedQuery<Managers> allQuery = sesh.createQuery(all);
            List<Managers> resultList = allQuery.getResultList();
            Set <Managers> managers = new HashSet<>(resultList);
            return managers;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Managers getManagerById(int id){
        try{
            Session sesh = sf.openSession();
            Managers manager = sesh.get(Managers.class,id);
            sesh.close();
            return manager;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Managers updateManager(Managers manager) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.update(manager);
            sesh.getTransaction().commit();
            sesh.close();
            return manager;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
   public boolean deleteManagerById(int id){
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.delete(this.getManagerById(id));
            sesh.getTransaction().commit();
            sesh.close();
            return true;
        }catch (HibernateException he){
            he.printStackTrace();
            return false;
        }

    }

}
