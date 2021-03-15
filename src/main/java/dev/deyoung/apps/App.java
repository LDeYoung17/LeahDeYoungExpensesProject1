package dev.deyoung.apps;

import dev.deyoung.controllers.ExpenseController;
import dev.deyoung.controllers.LoginController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(
                config -> {
                    config.enableCorsForAllOrigins();
                }
        );

        ExpenseController expenseController = new ExpenseController();
        LoginController loginController = new LoginController();

        app.post("/expenses", expenseController.createExpenseHandler);

        app.get("/expenses",expenseController.getAllExpensesHandler);
        app.get("/expenses/:id",expenseController.getExpenseByIdHandler);
        app.get("/expenses/employee/:id",expenseController.ExpenseByEmployeeIdHandler);
        app.get("/expenses/manager/:id",expenseController.ExpenseByManagerIdHandler);
        app.get("/expenses/manager/employee/:managerid/:id",expenseController.getExpenseByEmployeeIdPerManagerHandler);
        app.get("/expenses/employee/status/:id/:status",expenseController.getExpensesByStatusPerEmployee);
        app.get("/expenses/manager/status/:id/:status",expenseController.getExpensesByStatusPerManager);
        app.get("/expenses/manager/employee/status/:managerid/:employeeid/:status",expenseController.getExpensesByStatusPerEmployeePerManager);
        app.get("/expenses/employee/statusdate/:id/:statusdate",expenseController.getExpensesByStatusDatePerEmployee);
        app.get("/expenses/manager/statusdate/:id/:statusdate",expenseController.getExpensesByStatusDatePerManager);
        app.get("/expenses/manager/employee/statusdate/:managerid/:employeeid/:statusdate",expenseController.getExpensesByStatusDatePerEmployeePerManager);
        app.get("/expenses/employee/submitdate/:id/:submitdate",expenseController.getExpensesBySubmissionDatePerEmployee);
        app.get("/expenses/manager/submitdate/:id/:submitdate",expenseController.getExpensesBySubmissionDatePerManager);
        app.get("/expenses/manager/employee/submitdate/:managerid/:employeeid/:submitdate",expenseController.getExpensesBySubmissionDatePerEmployeePerManager);

        app.put("/expenses/:expenseid", expenseController.updateExpensesHandler);

        app.delete("/expenses/:expenseid", expenseController.deleteExpenseHandler);

        app.post("/users/login", loginController.loginHandler);

        app.start();

    }
}


