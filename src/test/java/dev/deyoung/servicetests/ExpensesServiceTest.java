package dev.deyoung.servicetests;

import dev.deyoung.daos.ExpensesDaoHibernate;
import dev.deyoung.entities.Employees;
import dev.deyoung.entities.Expenses;
import dev.deyoung.entities.Managers;
import dev.deyoung.services.ExpensesServiceImpl;
import dev.deyoung.services.ExpensesServices;
import org.junit.jupiter.api.*;
import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ExpensesServiceTest {


    private static Expenses testExpense = null;
    private static ExpensesServices expenseService = new ExpensesServiceImpl(new ExpensesDaoHibernate());
    Managers manager = new Managers(0, "Michigan", "Girl", "Somewhere in Michigan", "michigangirl", "imfrommichigan");
    Employees employee = new Employees(0, "Michigan", "Girlsfriend", "Somewhere in Michigan", 1, "michigangirlsfriend", "alsofrommichigan");


    @Test
    @Order(1)

    void create_expense_in_service(){
        Expenses expense = new Expenses(0, 50.00, "haircare", 20041003, 1, 1,  "Pending", 20041003, "none");
        expenseService.newExpense(expense);

        System.out.println(expense);
        System.out.println(expense.getExpenseAmount());

        Assertions.assertNotEquals(0, expense.getExpenseId());
        Assertions.assertNotEquals(null, expense.getStatus());
        testExpense = expense;

    }

    @Test
    @Order(2)

    void get_all_expenses_in_service(){

        Set<Expenses> expenseSet = new HashSet<Expenses>(expenseService.getAllExpenses());
        Set <Expenses> testExpensesSet = new HashSet<Expenses>();
        testExpensesSet.add(testExpense);

        Assertions.assertEquals(expenseSet.size(), testExpensesSet.size());

    }

    @Test
    @Order(3)

    void get_expense_by_id(){
        Expenses expense = testExpense;
        Expenses expenseIdTime = expenseService.getExpenseById(expense.getExpenseId());

        Assertions.assertEquals(expenseIdTime.getExpenseId(), testExpense.getExpenseId());

    }

    @Test
    @Order(4)

    void get_expense_by_employee_id(){
        Expenses expense = testExpense;
        Set<Expenses> expenseByEmployeeId = new HashSet<>(expenseService.getExpenseByEmployeeId(expense.getEmployeeId()));
        System.out.println(expenseByEmployeeId);
        System.out.println(expense);

        Assertions.assertFalse(expenseByEmployeeId.isEmpty());


    }

    @Test
    @Order(5)

    void get_expense_by_date_submitted(){
        Expenses expense = testExpense;
        Set<Expenses> expenseByDateSubmitted = new HashSet<>(expenseService.getExpenseByDateSubmitted(expense.getDateSubmitted()));
        System.out.println(expense);
        System.out.println(expenseByDateSubmitted);

        Assertions.assertFalse(expenseByDateSubmitted.isEmpty());


    }

    @Test
    @Order(6)

    void get_expense_by_status_date(){
        Expenses expense = testExpense;
        Set<Expenses> expenseByStatusDate = new HashSet<>(expenseService.getExpenseByStatusDate(expense.getStatusDate()));
        System.out.println(expense);
        System.out.println(expenseByStatusDate);

        Assertions.assertFalse(expenseByStatusDate.isEmpty());


    }

    @Test
    @Order(7)

    void get_expense_by_status(){
        Expenses expense = testExpense;
        Set<Expenses> expenseByStatus = new HashSet<>(expenseService.getExpenseByStatus(expense.getStatus()));
        System.out.println(expense.getStatus());
        System.out.println(expenseByStatus);
        Assertions.assertFalse(expenseByStatus.isEmpty());


    }

    @Test
    @Order(8)

    void get_expense_by_manager_id(){
        Expenses expense = testExpense;
        Set<Expenses> expenseByManagerId = new HashSet<>(expenseService.getExpenseByManagerId(expense.getManagerId()));
        System.out.println(expense);
        System.out.println(expenseByManagerId);

        Assertions.assertFalse(expenseByManagerId.isEmpty());



    }




    @Test
    @Order(10)

    void update_expense_amount(){

        Expenses expense = testExpense;
        Expenses updatedExpense = expenseService.updateExpenseAmount(expense, 200.00);

        System.out.println(updatedExpense.getExpenseAmount());
        System.out.println(updatedExpense);

        Assertions.assertNotEquals(0, updatedExpense.getExpenseAmount());

    }

    @Test
    @Order(11)
    void update_expense_reason(){

        Expenses expense = testExpense;

        Expenses updatedExpense = expenseService.updateExpenseReason(expense, "because I am SUCH a good friend");

        Assertions.assertNotEquals(updatedExpense.getReason(), null);

    }

    @Test
    @Order(12)
    void update_expense_status(){

        Expenses expense = testExpense;
        Expenses updatedExpense = expenseService.updateStatus(expense, "Approved");

        Assertions.assertNotEquals(updatedExpense.getStatus(), "Denied");

    }

    @Test
    @Order(13)
    void update_expense_status_date() {

        Expenses expense = testExpense;
        Expenses updatedExpense = expenseService.updateStatusDate(expense, 12122012);

        Assertions.assertNotEquals(updatedExpense.getStatusDate(), updatedExpense.getDateSubmitted());
    }


    @Test
    @Order(14)
    void update_manager_notes(){

        Expenses expense = testExpense;
        Expenses updatedExpense = expenseService.updateManagerNotes(expense, "stop trying to make fetch happen");

        Assertions.assertNotEquals(updatedExpense.getManagerNotes(), null);

    }

    @Test
    @Order(15)
    void get_manager_username(){
        Managers managerInService = expenseService.getManagerByUsername(manager.getUsername());

        Assertions.assertEquals(managerInService.getLastName(), manager.getLastName());


    }

    @Test
    @Order(16)
    void get_employee_username(){

        Employees employeeInService = expenseService.getEmployeeByUsername(employee.getUsername());

        Assertions.assertEquals(employeeInService.getLastName(), employee.getLastName());

    }


    @Test
    @Order(17)

    void delete_expense(){

        Expenses expense = testExpense;
        boolean deleted = expenseService.deleteExpenseById(expense.getExpenseId());

        System.out.println(expense.getExpenseId());

        Assertions.assertTrue(deleted);



    }
}
