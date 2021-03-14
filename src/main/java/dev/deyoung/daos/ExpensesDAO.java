package dev.deyoung.daos;

import dev.deyoung.entities.Expenses;

import java.util.Set;

public interface ExpensesDAO {

    //Create
    Expenses createExpense (Expenses expense);

    //Read
    Set<Expenses> getExpenses();
    Expenses getExpenseById(int id);

    //Update
    Expenses updateExpenses(Expenses expense);

    //Delete
    boolean deleteExpenseById(int id);

}
