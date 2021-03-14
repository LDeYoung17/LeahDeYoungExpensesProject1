package dev.deyoung.daos;

import dev.deyoung.entities.Employees;
import dev.deyoung.Utils.HibernateUtil;
import dev.deyoung.entities.Managers;
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

public class EmployeeDaoHibernate implements EmployeeDAO{

    private SessionFactory sf = HibernateUtil.createSF();

    @Override
    public Employees createEmployee (Employees employee) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(employee);
            sesh.getTransaction().commit();
            sesh.close();
            return employee;
        }catch(HibernateException he){
            he.printStackTrace();
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
            return employees;
        } catch (HibernateException he) {
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Employees getEmployeeById(int id){
        try{
            Session sesh = sf.openSession();
            Employees employee = sesh.get(Employees.class,id);
            sesh.close();
            return employee;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public Employees updateEmployee(Employees employee) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.update(employee);
            sesh.getTransaction().commit();
            sesh.close();
            return employee;
        }catch (HibernateException he){
            he.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteEmployeeById(int id){
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.delete(this.getEmployeeById(id));
            sesh.getTransaction().commit();
            sesh.close();
            return true;
        }catch (HibernateException he){
            he.printStackTrace();
            return false;
        }

    }

}
