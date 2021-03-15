package dev.deyoung.daos;

import dev.deyoung.controllers.ExpenseController;
import dev.deyoung.entities.Managers;
import dev.deyoung.Utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerDaoHibernate implements  ManagerDAO{

    private SessionFactory sf = HibernateUtil.createSF();
    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());

    @Override
    public Managers createManager(Managers manager) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(manager);
            sesh.getTransaction().commit();
            sesh.close();
            logger.info("Created manager in the DAO");
            return manager;
        }catch(HibernateException he){
            he.printStackTrace();
            logger.error(he);
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
            logger.info("Got all managers in the DAO successfully");
            return managers;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public Managers getManagerByUsername(String username) {
        try{
            Session sesh = sf.openSession();
            String hql = "from Managers m where m.username = :username";

            Query query = sesh.createQuery(hql);
            query.setParameter("username", username);
            List<Managers> managers = query.list();
            Managers manager = managers.get(0);
            sesh.close();
            logger.info("Got the manager by username in the DAO");
            return manager;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

}
