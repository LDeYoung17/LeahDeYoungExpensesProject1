package dev.deyoung.services;

import dev.deyoung.controllers.ExpenseController;
import dev.deyoung.daos.*;
import dev.deyoung.entities.Employees;
import dev.deyoung.entities.Expenses;
import java.util.HashSet;
import java.util.Set;

import dev.deyoung.entities.Managers;
import org.apache.log4j.Logger;

public class ExpensesServiceImpl implements ExpensesServices{


    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());

    private ExpensesDAO expenseDAO;
    private EmployeeDAO employeeDAO = new EmployeeDaoHibernate();
    private ManagerDAO managerDAO = new ManagerDaoHibernate();


    public ExpensesServiceImpl(ExpensesDAO expenseDAO) {this.expenseDAO = expenseDAO;}


    @Override
    public Expenses newExpense(Expenses expense) {
        try {
            this.expenseDAO.createExpense(expense);
            logger.info("The expense was created in the service");
        }catch(Exception e){
            logger.error(e);
        }
        return expense;
    }

    @Override
    public Set<Expenses> getAllExpenses() {
        return this.expenseDAO.getExpenses();
    }

    @Override
    public Expenses getExpenseById(int id) {
        return this.expenseDAO.getExpenseById(id);
    }

    @Override
    public Set<Expenses> getExpenseByEmployeeId(int employeeId) {
        Set<Expenses> allExpenses = this.expenseDAO.getExpenses();
        Set<Expenses> expensebyEmployeeId = new HashSet<Expenses>();

        for(Expenses expense : allExpenses) {
            try{
                if(expense.getEmployeeId() == employeeId) {
                    expensebyEmployeeId.add(expense);
                    logger.info("Grabbed expenses by Employee ID in the service.");
                }
            }catch(Exception e){
                logger.error(e);
            }


        }
        return expensebyEmployeeId;
    }

    @Override
    public Set<Expenses> getExpenseByDateSubmitted(int dateSubmitted) {
        Set<Expenses> allExpenses = this.expenseDAO.getExpenses();
        Set<Expenses> expenseByDateSubmitted = new HashSet<Expenses>();

        for(Expenses expense : allExpenses) {
            try {
                if (expense.getDateSubmitted() == dateSubmitted) {
                    expenseByDateSubmitted.add(expense);
                    logger.info("Grabbed expenses by date submitted in the service.");
                }
            }catch(Exception e){
                logger.error(e);
            }

        }
        return expenseByDateSubmitted;
    }

    @Override
    public Set<Expenses> getExpenseByStatusDate(int statusDate) {
        Set<Expenses> allExpenses = this.expenseDAO.getExpenses();
        Set<Expenses> expenseByStatusDate = new HashSet<Expenses>();

        for(Expenses expense : allExpenses) {
            try {
                if (expense.getStatusDate() == statusDate) {
                    expenseByStatusDate.add(expense);
                    logger.info("Grabbed expenses by status date in the service.");
                }
            }catch(Exception e){
                logger.error(e);
            }

        }
        return expenseByStatusDate;
    }

    @Override
    public Set<Expenses> getExpenseByStatus(String status) {
        Set<Expenses> allExpenses = this.expenseDAO.getExpenses();
        Set<Expenses> expenseByStatus = new HashSet<Expenses>();

        for(Expenses expense : allExpenses) {
            try {
                if (expense.getStatus().contains(status)) {
                    expenseByStatus.add(expense);
                    logger.info("Grabbed expenses by status in the service.");

                }
            }catch(Exception e){
                logger.error(e);
            }

        }
        return expenseByStatus;
    }

    @Override
    public Set<Expenses> getExpenseByManagerId(int managerId) {
        Set<Expenses> allExpenses = this.expenseDAO.getExpenses();
        Set<Expenses> expenseByManagerId = new HashSet<Expenses>();

        for(Expenses expense : allExpenses) {
            try {
                if (expense.getManagerId() == managerId) {
                    expenseByManagerId.add(expense);
                    logger.info("Grabbed expenses by Manager ID in the service.");

                }
            }catch(Exception e){
                logger.error(e);
            }

        }
        return expenseByManagerId;
    }

    @Override
    public Employees getEmployeeByUsername(String username) {
        return this.employeeDAO.getEmployeeByUsername(username);
    }

    @Override
    public Managers getManagerByUsername(String username) {
        return this.managerDAO.getManagerByUsername(username);
    }

    @Override
    public Expenses updateExpenseAmount(Expenses expense, double expenseAmount) {

       try{
           expense.setExpenseAmount(expenseAmount);
           this.expenseDAO.updateExpenses(expense);
           logger.info("Expense amount updated in the service.");
       }catch(Exception e){
           logger.error(e);
       }

        return expense;
    }

    @Override
    public Expenses updateExpenseReason(Expenses expense, String reason) {
        try {
            expense.setReason(reason);
            this.expenseDAO.updateExpenses(expense);
            logger.info("Expense reason updated in the service.");
        }catch(Exception e){
            logger.error(e);
        }

        return expense;
    }

    @Override
    public Expenses updateStatus(Expenses expense, String status) {
        try {
            expense.setStatus(status);
            this.expenseDAO.updateExpenses(expense);
            logger.info("Expense status updated in the service.");
        }catch(Exception e){
            logger.error(e);
        }

        return expense;
    }

    @Override
    public Expenses updateStatusDate(Expenses expense, int statusDate) {
        try{
            expense.setStatusDate(statusDate);
            this.expenseDAO.updateExpenses(expense);
            logger.info("Expense status date updated in the service.");

        }catch(Exception e){
            logger.error(e);
        }


        return expense;    }

    @Override
    public Expenses updateManagerNotes(Expenses expense, String managerNotes) {
        try{
            expense.setManagerNotes(managerNotes);
            this.expenseDAO.updateExpenses(expense);
            logger.info("Expense's manager notes updated in the service.");

        }catch(Exception e){
            logger.error(e);
        }

        return expense;
    }

    @Override
    public boolean deleteExpenseById(int expenseId) {
        boolean deleted = false;
        try {
            deleted = this.expenseDAO.deleteExpenseById(expenseId);
            logger.info("The expense was deleted in the service.");
        } catch (Exception e) {
            logger.error(e);
        }

        return deleted;
    }
}
