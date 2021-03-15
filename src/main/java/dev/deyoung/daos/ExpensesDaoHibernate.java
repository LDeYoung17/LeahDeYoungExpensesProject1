package dev.deyoung.daos;

import dev.deyoung.controllers.ExpenseController;
import dev.deyoung.entities.Expenses;
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
import org.apache.log4j.Logger;

public class ExpensesDaoHibernate implements ExpensesDAO{

    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());


    private SessionFactory sf = HibernateUtil.createSF();

    @Override
    public Expenses createExpense (Expenses expense) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.save(expense);
            sesh.getTransaction().commit();

//            sesh.getTransaction().begin();
//            String hql = "UPDATE Expense as e " + "SET e.managerId = :emp.managerId" + "LEFT JOIN Employee emp ON emp.employeeId = e.employeeId " + "WHERE e.employeeId :?";
//            Query query = sesh.createQuery(hql);
//            List<Expenses> expenses = query.list();
//            expense = expenses.get(0);
            logger.info("Created an expense in the DAO.");
            return expense;
        }catch(HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public Set<Expenses> getExpenses() {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            CriteriaBuilder cb = sesh.getCriteriaBuilder();
            CriteriaQuery<Expenses> cq = cb.createQuery(Expenses.class);
            Root<Expenses> rootEntry = cq.from(Expenses.class);
            CriteriaQuery<Expenses> all = cq.select(rootEntry);

            TypedQuery<Expenses> allQuery = sesh.createQuery(all);
            List<Expenses> resultList = allQuery.getResultList();
            Set <Expenses> expenses = new HashSet<>(resultList);
            logger.info("Grabbed all expenses in the DAO.");
            return expenses;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public Expenses getExpenseById(int id){
        try{
            Session sesh = sf.openSession();
            Expenses expense = sesh.get(Expenses.class,id);
            sesh.close();
            logger.info("Grabbed expense by ID " + id + " in the DAO.");

            return expense;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public Expenses updateExpenses(Expenses expense) {
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.update(expense);
            sesh.getTransaction().commit();
            sesh.close();
            logger.info("Updated expense in the DAO.");

            return expense;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return null;
        }
    }

    @Override
    public boolean deleteExpenseById(int id){
        try{
            Session sesh = sf.openSession();
            sesh.getTransaction().begin();
            sesh.delete(this.getExpenseById(id));
            sesh.getTransaction().commit();
            sesh.close();
            logger.info("Deleted expense in the DAO.");
            return true;
        }catch (HibernateException he){
            he.printStackTrace();
            logger.error(he);
            return false;
        }

    }

}
