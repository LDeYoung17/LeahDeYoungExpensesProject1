package dev.deyoung.apps;

import dev.deyoung.controllers.ExpenseController;
//import dev.deyoung.controllers.LoginController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {

        Javalin app = Javalin.create(
                config -> {
                    config.enableCorsForAllOrigins();
                }
        );

        ExpenseController expenseController = new ExpenseController();
        //LoginController loginController = new LoginController();

        app.post("/expenses", expenseController.createExpenseHandler);

        app.get("/expenses",expenseController.getAllExpensesHandler);
        app.get("/expenses/:id",expenseController.getExpenseByIdHandler);
        app.get("/expenses/employee/:id",expenseController.ExpenseByEmployeeIdHandler);
        app.get("/expenses/manager/:id",expenseController.ExpenseByManagerIdHandler);
        //app.get("/expenses/director/:id",expenseController.ExpenseByDirectorIdHandler);
        app.get("/expenses/manager/employee/:managerid/:id",expenseController.getExpenseByEmployeeIdPerManagerHandler);
        //app.get("/expenses/director/manager/:directorid/:managerid",expenseController.getExpenseByManagerIdHandler);
        //app.get("/expenses/director/manager/employee/:directorid/:managerid/:employeeid",expenseController.getExpenseByEmployeePerManagerIdHandler);
        //app.get("/expenses/director/employee/:directorid/:employeeid",expenseController.getExpenseByEmployeeIdPerDirectorHandler);
        app.get("/expenses/employee/status/:id/:status",expenseController.getExpensesByStatusPerEmployee);
        app.get("/expenses/manager/status/:id/:status",expenseController.getExpensesByStatusPerManager);
        //app.get("/expenses/director/status/:id/:status",expenseController.getExpensesByStatusPerDirector);
        app.get("/expenses/manager/employee/status/:managerid/:employeeid/:status",expenseController.getExpensesByStatusPerEmployeePerManager);
//        app.get("/expenses/director/manager/status/:directorid/:managerid/:status",expenseController.getExpensesByStatusPerManagerPerDirector);
//        app.get("/expenses/director/manager/employee/status/:directorid/:managerid/:employeeid/:status",expenseController.getExpensesByStatusPerEmployeePerManagerPerDirector);
//        app.get("/expenses/director/employee/status/:directorid/:employeeid/:status",expenseController.getExpensesByStatusPerEmployeePerDirector);
        app.get("/expenses/employee/statusdate/:id/:statusdate",expenseController.getExpensesByStatusDatePerEmployee);
        app.get("/expenses/manager/statusdate/:id/:statusdate",expenseController.getExpensesByStatusDatePerManager);
//        app.get("/expenses/director/statusdate/:id/:statusdate",expenseController.getExpensesByStatusDatePerDirector);
        app.get("/expenses/manager/employee/statusdate/:managerid/:employeeid/:statusdate",expenseController.getExpensesByStatusDatePerEmployeePerManager);
//        app.get("/expenses/director/manager/statusdate/:directorid/:managerid/:statusdate",expenseController.getExpensesByStatusDatePerManagerPerDirector);
//        app.get("/expenses/director/manager/employee/statusdate/:directorid/:managerid/:employeeid/:statusdate",expenseController.getExpensesByStatusDatePerEmployeePerManagerPerDirector);
//        app.get("/expenses/director/employee/statusdate/:directorid/:employeeid/:statusdate",expenseController.getExpensesByStatusDatePerEmployeePerDirector);
        app.get("/expenses/employee/submitdate/:id/:submitdate",expenseController.getExpensesBySubmissionDatePerEmployee);
        app.get("/expenses/manager/submitdate/:id/:submitdate",expenseController.getExpensesBySubmissionDatePerManager);
//        app.get("/expenses/director/submitdate/:id/:submitdate",expenseController.getExpensesBySubmissionDatePerDirector);
        app.get("/expenses/manager/employee/submitdate/:managerid/:employeeid/:submitdate",expenseController.getExpensesBySubmissionDatePerEmployeePerManager);
//        app.get("/expenses/director/manager/submitdate/:directorid/:managerid/:submitdate",expenseController.getExpensesBySubmissionDatePerManagerPerDirector);
//        app.get("/expenses/director/manager/employee/submitdate/:directorid/:managerid/:employeeid/:submitdate",expenseController.getExpensesBySubmissionDatePerEmployeePerManagerPerDirector);
//        app.get("/expenses/director/employee/submitdate/:directorid/:employeeid/:submitdate",expenseController.getExpensesBySubmissionDatePerEmployeePerDirector);

        app.put("/expenses/:expenseid", expenseController.updateExpensesHandler);

        app.delete("/expenses/:expenseid", expenseController.deleteExpenseHandler);

        app.start();

    }
}


