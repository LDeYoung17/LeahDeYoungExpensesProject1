package dev.deyoung.daos;

import dev.deyoung.entities.Employees;
import dev.deyoung.entities.Login;
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

public class LoginDaoHibernate implements LoginDAO{

    private SessionFactory sf = HibernateUtil.createSF();

    @Override
    public Login createLogin (Login login) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(login);
            sesh.getTransaction().commit();
            sesh.close();
            return login;
        }catch(HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<Login> getLogins() {
        try {
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            CriteriaBuilder cb = sesh.getCriteriaBuilder();
            CriteriaQuery<Login> cq = cb.createQuery(Login.class);
            Root<Login> rootEntry = cq.from(Login.class);
            CriteriaQuery<Login> all = cq.select(rootEntry);

            TypedQuery<Login> allQuery = sesh.createQuery(all);
            List<Login> resultList = allQuery.getResultList();
            Set<Login> logins = new HashSet<>(resultList);
            sesh.close();
            return logins;
        } catch (HibernateException he) {
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Login getLoginById(int id){
        try{
            Session sesh = sf.openSession();
            Login login = sesh.get(Login.class,id);
            sesh.close();
            return login;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Login updateLogin(Login login) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.update(login);
            sesh.getTransaction().commit();
            sesh.close();
            return login;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteLoginById(int id){
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.delete(this.getLoginById(id));
            sesh.getTransaction().commit();
            sesh.close();
            return true;
        }catch (HibernateException he){
            he.printStackTrace();
            return false;
        }

    }

}
