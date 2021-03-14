package dev.deyoung.services;

import dev.deyoung.entities.Expenses;
import java.util.Set;

public interface ExpensesServices {

    //Create
    Expenses newExpense (Expenses expense);

    //Read
    Set<Expenses>getAllExpenses();
    Expenses getExpenseById(int id);
    Set<Expenses> getExpenseByEmployeeId(int employeeId);
    Set<Expenses> getExpenseByDateSubmitted(int dateSubmitted);
    Set<Expenses> getExpenseByStatusDate(int statusDate);
    Set<Expenses> getExpenseByStatus(String status);
    Set<Expenses> getExpenseByManagerId(int managerId);
    Set<Expenses> getExpenseByDirectorId(int directorId);


    //Update
//    Expenses updateEmployeeId(Expenses expense, int employeeId);
    Expenses updateExpenseAmount(Expenses expense, double expenseAmount);
    Expenses updateExpenseReason(Expenses expense, String reason);
    Expenses updateStatus(Expenses expense, String status);
    Expenses updateStatusDate(Expenses expense, int statusDate);
    Expenses updateManagerNotes(Expenses expense, String managerNotes);

    //Delete
    boolean deleteExpenseById(int expenseId);
}
