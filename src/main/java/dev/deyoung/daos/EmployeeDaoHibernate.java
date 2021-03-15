package dev.deyoung.daos;

import dev.deyoung.controllers.ExpenseController;
import dev.deyoung.entities.Employees;
import dev.deyoung.Utils.HibernateUtil;
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
import org.apache.log4j.Logger;


public class EmployeeDaoHibernate implements EmployeeDAO{

    private SessionFactory sf = HibernateUtil.createSF();
    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());


    @Override
    public Employees createEmployee (Employees employee) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(employee);
            sesh.getTransaction().commit();
            sesh.close();
            logger.info("Created employee in the DAO");
            return employee;

        }catch(HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public Set<Employees> getEmployees() {
        try {
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            CriteriaBuilder cb = sesh.getCriteriaBuilder();
            CriteriaQuery<Employees> cq = cb.createQuery(Employees.class);
            Root<Employees> rootEntry = cq.from(Employees.class);
            CriteriaQuery<Employees> all = cq.select(rootEntry);

            TypedQuery<Employees> allQuery = sesh.createQuery(all);
            List<Employees> resultList = allQuery.getResultList();
            Set<Employees> employees = new HashSet<>(resultList);
            logger.info("Got all employees in the DAO successfully");

            return employees;
        } catch (HibernateException he) {
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public Employees getEmployeeByUsername(String username) {
        try{
            Session sesh = sf.openSession();
            //Employees employee = sesh.get(Employees.class,username);
            String hql = "from Employees e where e.username = :username";

            Query query = sesh.createQuery(hql);
            query.setParameter("username", username);
            List<Employees> employees = query.list();
            Employees employee = employees.get(0);
            sesh.close();
            logger.info("Got the employee by username in the DAO successfully");
            return employee;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }
}
