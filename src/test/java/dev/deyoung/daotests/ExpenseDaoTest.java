package dev.deyoung.daotests;

import dev.deyoung.daos.*;
import dev.deyoung.entities.Expenses;
import org.junit.jupiter.api.*;
import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ExpenseDaoTest {

    private static ExpensesDAO expenseDAO = new ExpensesDaoHibernate();

    private static Expenses testExpenses = null;

    @Test
    @Order(1)
    void create_expense() {

        Expenses travel = new Expenses(0, 50.00, "went to friends art show", 10032004, 1, 1, "Pending", 10032004, "none");
        expenseDAO.createExpense(travel);
        System.out.println(travel);
        Assertions.assertEquals(travel.getExpenseAmount(), 50.00);
        testExpenses = travel;
    }

    @Test
    @Order(2)
    void get_all_expenses(){

        Set expenses = new HashSet(expenseDAO.getExpenses());
        System.out.println(expenses);

    }

    @Test
    @Order(3)
    void get_expense_by_id() {

        int expenseId = testExpenses.getExpenseId();
        System.out.println(expenseId);
        Expenses expense = expenseDAO.getExpenseById(expenseId);
        System.out.println(expense);
        Assertions.assertEquals(testExpenses.getExpenseAmount(), expense.getExpenseAmount());
        System.out.println(testExpenses.getEmployeeId() + "'s ID number is " + testExpenses.getExpenseId());

    }


    @Test
    @Order(4)
    void update_expense() {

        Expenses expense = expenseDAO.getExpenseById(testExpenses.getExpenseId());
        expense.setStatus("Approved");
        expenseDAO.updateExpenses(expense);
        Expenses updatedExpense = expenseDAO.getExpenseById(expense.getExpenseId());
        Assertions.assertEquals(updatedExpense.getStatus(), expense.getStatus());
        System.out.println("Expense ID " + testExpenses.getExpenseId() + "'s status has been updated to " + testExpenses.getStatus());

    }



    @Test
    @Order(5)
    void delete_expense() {

        int expenseId = testExpenses.getExpenseId();
        System.out.println(expenseId);
        boolean deleted = expenseDAO.deleteExpenseById(expenseId);
        System.out.println(deleted);
        Assertions.assertTrue(deleted);


    }

}







