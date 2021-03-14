package dev.deyoung.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dev.deyoung.daos.ExpensesDAO;
import dev.deyoung.daos.ExpensesDaoHibernate;
import dev.deyoung.daos.LoginDaoHibernate;
import dev.deyoung.entities.Expenses;
import dev.deyoung.entities.Login;
import dev.deyoung.services.ExpensesServiceImpl;
import dev.deyoung.services.ExpensesServices;
import dev.deyoung.services.LoginService;
import dev.deyoung.services.LoginServiceImpl;
import io.javalin.http.Handler;
import java.util.HashSet;
import java.util.Set;
import dev.deyoung.Utils.JWTUtil;

import org.apache.log4j.Logger;



public class ExpenseController {


    private ExpensesServices expensesService = new ExpensesServiceImpl(new ExpensesDaoHibernate());
    private static ExpensesDAO expenseDAO = new ExpensesDaoHibernate();

    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());

    //private LoginService loginService = new LoginServiceImpl(new LoginDaoHibernate());

    //Methods
    public Set<Expenses> ExpensesByEmployeeIdPerManager(int employeeId, int managerId) {

        Set<Expenses> expenseByManagerId = new HashSet<>(this.expensesService.getExpenseByManagerId(managerId));
        Set<Expenses> expenseByEmployeeId = new HashSet<>();

        if (expenseByManagerId.isEmpty()) {
            return null;
        } else {
            for (Expenses expense : expenseByManagerId) {
                if (expense.getEmployeeId() == employeeId) {
                    expenseByEmployeeId.add(expense);
                }
            }
            return expenseByEmployeeId;
        }
    }

    public Set<Expenses> ExpensesByManagerIdPerDirector(int directorId, int managerId) {
        Set<Expenses> expenseByDirectorId = new HashSet<>(this.expensesService.getExpenseByDirectorId(directorId));
        Set<Expenses> expenseByManagerId = new HashSet<>();

        if (expenseByDirectorId.isEmpty()) {
            return null;
        } else {
            for (Expenses expense : expenseByDirectorId) {
                if (expense.getManagerId() == managerId) {
                    expenseByManagerId.add(expense);
                }
            }
            return expenseByManagerId;
        }
    }

    public Set<Expenses> ExpensesByEmployeeIdPerDirector(int directorId, int employeeId) {

        Set<Expenses> expenseByDirectorId = new HashSet<>(this.expensesService.getExpenseByDirectorId(directorId));
        Set<Expenses> expenseByEmployeeId = new HashSet<>();


        if (expenseByDirectorId.isEmpty()) {
            return null;
        } else {
            for (Expenses expense : expenseByDirectorId) {
                if (expense.getEmployeeId() == employeeId) {
                    expenseByEmployeeId.add(expense);
                }
            }
            return expenseByEmployeeId;
        }
    }

//Handlers

    //Creating expenses
    public Handler createExpenseHandler = (ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Expenses expense = gson.fromJson(body, Expenses.class);

        try {
            this.expensesService.newExpense(expense);
            String json = gson.toJson(expense);
            ctx.result(json);
            ctx.status(201);
            logger.info("Expense created");
        } catch (Exception e) {
            ctx.result("Could not create expense!");
            ctx.status(404);
            logger.error(e);
        }
    };

    //Getting all expenses
    public Handler getAllExpensesHandler = (ctx) -> {

        Set<Expenses> allExpenses = this.expensesService.getAllExpenses();
        Gson gson = new Gson();
        String expensesJSON = gson.toJson(allExpenses);
        ctx.result(expensesJSON);
        ctx.status(200);

    };

    public Handler getExpenseByIdHandler = (ctx) -> {

        int id = Integer.parseInt(ctx.pathParam("id"));
        Expenses expense = this.expensesService.getExpenseById(id);
        if (expense == null) {
            ctx.result("Expense not found");
            ctx.status(404);
        } else {
            Gson gson = new Gson();
            String expenseJSON = gson.toJson(expense);
            ctx.result(expenseJSON);
            ctx.status(200);
        }


    };

    public Handler ExpenseByEmployeeIdHandler = (ctx) ->{
        int id = Integer.parseInt(ctx.pathParam("id"));
        Set<Expenses> expensesbyEmployeeId = new HashSet<>(this.expensesService.getExpenseByEmployeeId(id));
        if (expensesbyEmployeeId.isEmpty()) {
            ctx.result("Expenses not found");
            ctx.status(404);
        } else {
            Gson gson = new Gson();
            String accountJSON = gson.toJson(expensesbyEmployeeId);
            ctx.result(accountJSON);
            ctx.status(200);
        }
    };

    public Handler ExpenseByManagerIdHandler = (ctx) ->{
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Set<Expenses> expensesbyManagerId = new HashSet<>(this.expensesService.getExpenseByManagerId(id));
            try{
                if (expensesbyManagerId.isEmpty()) {
                    ctx.result("Expenses not found");
                    ctx.status(404);
                    logger.info("Expenses were not found for the manager with ID" + id);
                } else {
                    Gson gson = new Gson();
                    String accountJSON = gson.toJson(expensesbyManagerId);
                    ctx.result(accountJSON);
                    ctx.status(200);
                    logger.info("Expenses were found for the manager with ID" + id);
                }
            }catch(Exception e){
                logger.error(e);
            }
        }else{
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Someone tried to get expenses by manager but couldn't!");
        }
    };

//    public Handler ExpenseByDirectorIdHandler = (ctx) ->{
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int id = Integer.parseInt(ctx.pathParam("id"));
//            Set<Expenses> expensesbyDirectorId = new HashSet<>(this.expensesService.getExpenseByDirectorId(id));
//            try {
//                if (expensesbyDirectorId.isEmpty()) {
//                    ctx.result("Expenses not found");
//                    ctx.status(404);
//                    logger.info("Expenses were not found for the director with ID" + id);
//                } else {
//                    Gson gson = new Gson();
//                    String accountJSON = gson.toJson(expensesbyDirectorId);
//                    ctx.result(accountJSON);
//                    ctx.status(200);
//                    logger.info("Expenses were found for the director with ID" + id);
//                }
//            }catch(Exception e){
//                logger.error(e);
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to get expenses by director but couldn't!");
//        }
//    };


    public Handler getExpenseByEmployeeIdPerManagerHandler = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {

            int id = Integer.parseInt(ctx.pathParam("id"));
            int managerId = Integer.parseInt(ctx.pathParam("managerid"));

            Set<Expenses> expenseByEmployeeId = this.ExpensesByEmployeeIdPerManager(id, managerId);
            try {
                if (expenseByEmployeeId.isEmpty()) {
                    ctx.result("Expenses for this employee not found");
                    ctx.status(404);
                    logger.info("Expenses for this employee of this manager were not found");
                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expenseByEmployeeId);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Expenses for this employee of this manager were found");

                }
            }catch(Exception e){
                logger.error(e);
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Someone tried to get expenses by employee per manager but couldn't!");
        }


    };

//    public Handler getExpenseByManagerIdHandler = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//
//            Set<Expenses> expenseByManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//
//            if (expenseByManagerId.isEmpty()) {
//                ctx.result("Expenses for the manager not found");
//                ctx.status(404);
//                logger.info("Expenses for this manager of this director were not found");
//
//            } else {
//                Gson gson = new Gson();
//                String expenseJSON = gson.toJson(expenseByManagerId);
//                ctx.result(expenseJSON);
//                ctx.status(200);
//                logger.info("Expenses for this manager of this director were found");
//
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to get manager expenses for a director but couldn't!");
//        }
//    };
//
//    public Handler getExpenseByEmployeePerManagerIdHandler = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//
//            Set<Expenses> expenseByEmployeeId = null;
//            Set<Expenses> expenseByManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//
//            try {
//                if (expenseByManagerId.isEmpty()) {
//                    ctx.result("Expenses for this manager not found");
//                    ctx.status(404);
//                } else {
//                    expenseByEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
//                }
//
//                if (expenseByEmployeeId.isEmpty()) {
//                    ctx.result("Expenses for this employee not found");
//                    ctx.status(404);
//                    logger.info("Expenses for this employee of this manager of this director were not found");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expenseByEmployeeId);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Expenses for this employee of this manager of this director were  found");
//
//                }
//            }catch(Exception e){
//                logger.error(e);
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to do a big operation for a director but couldn't!");
//        }
//    };
//
//    public Handler getExpenseByEmployeeIdPerDirectorHandler = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//
//            Set<Expenses> expenseByEmployeeId = this.ExpensesByEmployeeIdPerDirector(directorId, employeeId);
//            try {
//                if (expenseByEmployeeId.isEmpty()) {
//                    ctx.result("Expenses for this employee not found");
//                    ctx.status(404);
//                    logger.info("Expenses for this employee of this director were not found");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expenseByEmployeeId);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Expenses for this employee of this director were found");
//
//                }
//            }catch(Exception e){
//                logger.error(e);
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to get an employee for a director but couldn't!");
//        }
//
//    };

    public Handler getExpensesByStatusPerEmployee  = (ctx) -> {

        int id = Integer.parseInt(ctx.pathParam("id"));
        String status = ctx.pathParam("status");
        Set<Expenses> expensesbyEmployeeId = new HashSet<>(this.expensesService.getExpenseByEmployeeId(id));
        Set<Expenses> expensesByStatus = new HashSet<>();

        if (expensesbyEmployeeId.isEmpty()) {
            ctx.result("Expenses not found");
            ctx.status(404);
        } else {
            for (Expenses expense : expensesbyEmployeeId){
                if(status.equals(expense.getStatus()) ) {
                    expensesByStatus.add(expense);
                }
            }
            if(expensesByStatus.isEmpty()){
                ctx.result("Expenses of this status not found");
                ctx.status(404);
                logger.info("Expenses of this status for this employee were not found");

            } else {
                Gson gson = new Gson();
                String expenseJSON = gson.toJson(expensesByStatus);
                ctx.result(expenseJSON);
                ctx.status(200);
                logger.info("Expenses of this status for this employee were found");

            }
        }
    };

    public Handler getExpensesByStatusPerManager  = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String status = ctx.pathParam("status");
            Set<Expenses> expensesbyManagerId = new HashSet<>(this.expensesService.getExpenseByManagerId(id));
            Set<Expenses> expensesByStatus = new HashSet<>();

            if (expensesbyManagerId.isEmpty()) {
                ctx.result("Expenses not found");
                ctx.status(404);
            } else {
                for (Expenses expense : expensesbyManagerId) {
                    if (status.equals(expense.getStatus())) {
                        expensesByStatus.add(expense);
                    }
                }
                if (expensesByStatus.isEmpty()) {
                    ctx.result("Expenses of this status not found");
                    ctx.status(404);
                    logger.info("Expenses of this status for this manager were not found");

                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expensesByStatus);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Expenses of this status for this manager were found");
                }
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Someone tried to get an expense by status for a manager but couldn't!");
        }
    };

//    public Handler getExpensesByStatusPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int id = Integer.parseInt(ctx.pathParam("id"));
//            String status = ctx.pathParam("status");
//            Set<Expenses> expensesbyDirectorId = new HashSet<>(this.expensesService.getExpenseByDirectorId(id));
//            Set<Expenses> expensesByStatus = new HashSet<>();
//
//            if (expensesbyDirectorId.isEmpty()) {
//                ctx.result("Expenses not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyDirectorId) {
//                    if (status.equals(expense.getStatus())) {
//                        expensesByStatus.add(expense);
//                    }
//                }
//                if (expensesByStatus.isEmpty()) {
//                    ctx.result("Expenses of this status not found");
//                    ctx.status(404);
//                    logger.info("Expenses of this status for this director were not found");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatus);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Expenses of this status for this director were found");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to get an expense by status for a director but couldn't!");
//        }
//    };

    public Handler getExpensesByStatusPerEmployeePerManager  = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
            String status = ctx.pathParam("status");

            Set<Expenses> expensesbyEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
            Set<Expenses> expensesByStatus = new HashSet<>();

            if (expensesbyEmployeeId.isEmpty()) {
                ctx.result("Expenses for this employee not found");
                ctx.status(404);
            } else {
                for (Expenses expense : expensesbyEmployeeId) {
                    if (status.equals(expense.getStatus())) {
                        expensesByStatus.add(expense);
                    }
                }
                if (expensesByStatus.isEmpty()) {
                    ctx.result("Expenses of this status not found");
                    ctx.status(404);
                    logger.info("Expenses of this status for this query were not found");

                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expensesByStatus);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Expenses of this status for this query were found");

                }
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Someone tried to do the thing but couldn't!");
        }
    };

//    public Handler getExpensesByStatusPerManagerPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            String status = ctx.pathParam("status");
//
//            Set<Expenses> expensesbyManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//            Set<Expenses> expensesByStatus = new HashSet<>();
//
//            if (expensesbyManagerId.isEmpty()) {
//                ctx.result("Expenses for this manager not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyManagerId) {
//                    if (status.equals(expense.getStatus())) {
//                        expensesByStatus.add(expense);
//                    }
//                }
//                if (expensesByStatus.isEmpty()) {
//                    ctx.result("Expenses of this status not found");
//                    ctx.status(404);
//                    logger.info("Expenses of this status for this query were not found again");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatus);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Expenses of this status for this query were found again");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to do the thing again but couldn't!");
//        }
//    };
//
//    public Handler getExpensesByStatusPerEmployeePerManagerPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            String status = ctx.pathParam("status");
//
//            Set<Expenses> expenseByEmployeeId = null;
//            Set<Expenses> expenseByManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//            Set<Expenses> expensesByStatus = new HashSet<>();
//
//            if (expenseByManagerId.isEmpty()) {
//                ctx.result("Expenses for this manager not found");
//                ctx.status(404);
//            } else {
//                expenseByEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
//            }
//
//            if (expenseByEmployeeId.isEmpty()) {
//                ctx.result("Expenses not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expenseByEmployeeId) {
//                    if (status.equals(expense.getStatus())) {
//                        expensesByStatus.add(expense);
//                    }
//                }
//                if (expensesByStatus.isEmpty()) {
//                    ctx.result("Expenses of this status not found");
//                    ctx.status(404);
//                    logger.info("Expenses of this status for this query were found yet again");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatus);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Expenses of this status for this query were found yet again");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Someone tried to do the thing yet again but couldn't!");
//        }
//    };
//
//    public Handler getExpensesByStatusPerEmployeePerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            String status = ctx.pathParam("status");
//
//            Set<Expenses> expensesbyDirectorId = this.ExpensesByEmployeeIdPerDirector(directorId, employeeId);
//            Set<Expenses> expensesByStatus = new HashSet<>();
//
//            if (expensesbyDirectorId.isEmpty()) {
//                ctx.result("Expenses for this director not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyDirectorId) {
//                    if (status.equals(expense.getStatus())) {
//                        expensesByStatus.add(expense);
//                    }
//                }
//                if (expensesByStatus.isEmpty()) {
//                    ctx.result("Expenses of this status not found");
//                    ctx.status(404);
//                    logger.info("Oh no!");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatus);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Hurray!");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Honestly please stop");
//        }
//    };

    public Handler getExpensesByStatusDatePerEmployee  = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int statusDate = Integer.parseInt(ctx.pathParam("statusdate"));
        Set<Expenses> expensesbyEmployeeId = new HashSet<>(this.expensesService.getExpenseByEmployeeId(id));
        Set<Expenses> expensesByStatusDate = new HashSet<>();

        if (expensesbyEmployeeId.isEmpty()) {
            ctx.result("Expenses not found");
            ctx.status(404);
        } else {
            for (Expenses expense : expensesbyEmployeeId){
                if(statusDate == expense.getStatusDate()) {
                    expensesByStatusDate.add(expense);
                }
            }
            if(expensesByStatusDate.isEmpty()){
                ctx.result("Expenses of this status date not found");
                ctx.status(404);
                logger.info("Nope, sorry!");

            } else {
                Gson gson = new Gson();
                String expenseJSON = gson.toJson(expensesByStatusDate);
                ctx.result(expenseJSON);
                ctx.status(200);
                logger.info("Hurray! You did it!");

            }
        }
    };

    public Handler getExpensesByStatusDatePerManager  = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            int statusDate = Integer.parseInt(ctx.pathParam("statusdate"));
            Set<Expenses> expensesbyManagerId = new HashSet<>(this.expensesService.getExpenseByManagerId(id));
            Set<Expenses> expensesByStatusDate = new HashSet<>();

            if (expensesbyManagerId.isEmpty()) {
                ctx.result("Expenses not found");
                ctx.status(404);
            } else {
                for (Expenses expense : expensesbyManagerId) {
                    if (statusDate == (expense.getStatusDate())) {
                        expensesByStatusDate.add(expense);
                    }
                }
                if (expensesByStatusDate.isEmpty()) {
                    ctx.result("Expenses of this status date not found");
                    ctx.status(404);
                    logger.info("This did not work!");

                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expensesByStatusDate);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Hurray! It worked!");

                }
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Can't get in sorry");
        }
    };

//    public Handler getExpensesByStatusDatePerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//        int id = Integer.parseInt(ctx.pathParam("id"));
//        int statusDate =  Integer.parseInt(ctx.pathParam("statusdate"));
//        Set<Expenses> expensesbyDirectorId = new HashSet<>(this.expensesService.getExpenseByDirectorId(id));
//        Set<Expenses> expensesByStatusDate = new HashSet<>();
//
//        if (expensesbyDirectorId.isEmpty()) {
//            ctx.result("Expenses not found");
//            ctx.status(404);
//        } else {
//            for (Expenses expense : expensesbyDirectorId){
//                if(statusDate == expense.getStatusDate()) {
//                    expensesByStatusDate.add(expense);
//                }
//            }
//            if(expensesByStatusDate.isEmpty()){
//                ctx.result("Expenses of this status date not found");
//                ctx.status(404);
//                logger.info("It did not work sorry");
//
//            } else {
//                Gson gson = new Gson();
//                String expenseJSON = gson.toJson(expensesByStatusDate);
//                ctx.result(expenseJSON);
//                ctx.status(200);
//                logger.info("Hurray! You found me!");
//
//            }
//        }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("YOU! SHALL NOT! PASS!");
//        }
//    };

    public Handler getExpensesByStatusDatePerEmployeePerManager  = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
            int statusDate =  Integer.parseInt(ctx.pathParam("statusdate"));

            Set<Expenses> expensesbyEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
            Set<Expenses> expensesByStatusDate = new HashSet<>();

            if (expensesbyEmployeeId.isEmpty()) {
                ctx.result("Expenses for this employee not found");
                ctx.status(404);
            } else {
                for (Expenses expense : expensesbyEmployeeId){
                    if(statusDate == expense.getStatusDate()) {
                        expensesByStatusDate.add(expense);
                    }
                }
                if(expensesByStatusDate.isEmpty()){
                    ctx.result("Expenses of this status date not found");
                    ctx.status(404);
                    logger.info("Nope.");
                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expensesByStatusDate);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Honestly congrats. This was a weird one.");

                }
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("GANDAAAAAALF!!! NOOOOOOOOOO!!!!");
        }
    };

//    public Handler getExpensesByStatusDatePerManagerPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            int statusDate =  Integer.parseInt(ctx.pathParam("statusdate"));
//
//            Set<Expenses> expensesbyManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//            Set<Expenses> expensesByStatusDate = new HashSet<>();
//
//            if (expensesbyManagerId.isEmpty()) {
//                ctx.result("Expenses for this manager not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyManagerId){
//                    if(statusDate == expense.getStatusDate()) {
//                        expensesByStatusDate.add(expense);
//                    }
//                }
//                if(expensesByStatusDate.isEmpty()){
//                    ctx.result("Expenses of this status date not found");
//                    ctx.status(404);
//                    logger.info("Hello. It's not me you're looking for!");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatusDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Hello. Is it me you're looking for?");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Sorry! Wrong key!");
//        }
//    };
//
//    public Handler getExpensesByStatusDatePerEmployeePerManagerPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            int statusDate = Integer.parseInt(ctx.pathParam("statusdate"));
//
//            Set<Expenses> expenseByEmployeeId = null;
//            Set<Expenses> expenseByManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//            Set<Expenses> expensesByStatusDate = new HashSet<>();
//
//            if (expenseByManagerId.isEmpty()) {
//                ctx.result("Expenses for this manager not found");
//                ctx.status(404);
//            } else {
//                expenseByEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
//            }
//
//            if (expenseByEmployeeId.isEmpty()) {
//                ctx.result("Expenses not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expenseByEmployeeId) {
//                    if (statusDate == expense.getStatusDate()) {
//                        expensesByStatusDate.add(expense);
//                    }
//                }
//                if (expensesByStatusDate.isEmpty()) {
//                    ctx.result("Expenses of this status date not found");
//                    ctx.status(404);
//                    logger.info("Not found here!");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatusDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Good day");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("If there's a key, there has to be a lock");
//        }
//    };
//
//    public Handler getExpensesByStatusDatePerEmployeePerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            int statusDate = Integer.parseInt(ctx.pathParam("statusdate"));
//
//            Set<Expenses> expensesbyDirectorId = this.ExpensesByEmployeeIdPerDirector(directorId, employeeId);
//            Set<Expenses> expensesByStatusDate = new HashSet<>();
//
//            if (expensesbyDirectorId.isEmpty()) {
//                ctx.result("Expenses for this director not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyDirectorId) {
//                    if (statusDate == expense.getStatusDate()) {
//                        expensesByStatusDate.add(expense);
//                    }
//                }
//                if (expensesByStatusDate.isEmpty()) {
//                    ctx.result("Expenses of this status date not found");
//                    ctx.status(404);
//                    logger.info("And none for Gretchen Wieners bye!");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesByStatusDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("You go Glen Coco!");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Hello darkness my old friend");
//        }
//    };

    public Handler getExpensesBySubmissionDatePerEmployee  = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));
        Set<Expenses> expensesbyEmployeeId = new HashSet<>(this.expensesService.getExpenseByEmployeeId(id));
        Set<Expenses> expensesBySubmissionDate = new HashSet<>();

        if (expensesbyEmployeeId.isEmpty()) {
            ctx.result("Expenses not found");
            ctx.status(404);
        } else {
            for (Expenses expense : expensesbyEmployeeId) {
                if (submitDate == expense.getDateSubmitted()) {
                    expensesBySubmissionDate.add(expense);
                }
            }
            if (expensesBySubmissionDate.isEmpty()) {
                ctx.result("Expenses of this submission date not found");
                ctx.status(404);
                logger.info("No expenses here");

            } else {
                Gson gson = new Gson();
                String expenseJSON = gson.toJson(expensesBySubmissionDate);
                ctx.result(expenseJSON);
                ctx.status(200);
                logger.info("You found the ever elusive expenses");

            }
        }
    };

    public Handler getExpensesBySubmissionDatePerManager  = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int id = Integer.parseInt(ctx.pathParam("id"));
            int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));
            Set<Expenses> expensesbyManagerId = new HashSet<>(this.expensesService.getExpenseByManagerId(id));
            Set<Expenses> expensesBySubmissionDate = new HashSet<>();

            if (expensesbyManagerId.isEmpty()) {
                ctx.result("Expenses not found");
                ctx.status(404);
            } else {
                for (Expenses expense : expensesbyManagerId) {
                    if (submitDate == (expense.getDateSubmitted())) {
                        expensesBySubmissionDate.add(expense);
                    }
                }
                if (expensesBySubmissionDate.isEmpty()) {
                    ctx.result("Expenses of this submission date not found");
                    ctx.status(404);
                    logger.info("Wrong.");
                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expensesBySubmissionDate);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Right!");
                }
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Ive come to talk with you again");
        }

    };

//    public Handler getExpensesBySubmissionDatePerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int id = Integer.parseInt(ctx.pathParam("id"));
//            int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));
//            Set<Expenses> expensesbyDirectorId = new HashSet<>(this.expensesService.getExpenseByDirectorId(id));
//            Set<Expenses> expensesBySubmissionDate = new HashSet<>();
//
//            if (expensesbyDirectorId.isEmpty()) {
//                ctx.result("Expenses not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyDirectorId) {
//                    if (submitDate == expense.getDateSubmitted()) {
//                        expensesBySubmissionDate.add(expense);
//                    }
//                }
//                if (expensesBySubmissionDate.isEmpty()) {
//                    ctx.result("Expenses of this submission date not found");
//                    ctx.status(404);
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesBySubmissionDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Because a vision softly creeping");
//        }
//    };

    public Handler getExpensesBySubmissionDatePerEmployeePerManager  = (ctx) -> {
        String jwt = ctx.header("Authorization");
        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

        if(decodedJWT.getClaim("role").asString().equals("Manager")) {
            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
            int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));

            Set<Expenses> expensesbyEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
            Set<Expenses> expensesBySubmissionDate = new HashSet<>();

            if (expensesbyEmployeeId.isEmpty()) {
                ctx.result("Expenses for this employee not found");
                ctx.status(404);
            } else {
                for (Expenses expense : expensesbyEmployeeId) {
                    if (submitDate == expense.getDateSubmitted()) {
                        expensesBySubmissionDate.add(expense);
                    }
                }
                if (expensesBySubmissionDate.isEmpty()) {
                    ctx.result("Expenses of this submission date not found");
                    ctx.status(404);
                    logger.info("Expenses of this submission date not found");

                } else {
                    Gson gson = new Gson();
                    String expenseJSON = gson.toJson(expensesBySubmissionDate);
                    ctx.result(expenseJSON);
                    ctx.status(200);
                    logger.info("Expenses of this submission date were found");

                }
            }
        }else {
            ctx.status(403);
            ctx.result("Improper authentication");
            logger.info("Left its seeds while I was sleeping");
        }
    };

//    public Handler getExpensesBySubmissionDatePerManagerPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));
//
//            Set<Expenses> expensesbyManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//            Set<Expenses> expensesBySubmissionDate = new HashSet<>();
//
//            if (expensesbyManagerId.isEmpty()) {
//                ctx.result("Expenses for this manager not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyManagerId) {
//                    if (submitDate == expense.getDateSubmitted()) {
//                        expensesBySubmissionDate.add(expense);
//                    }
//                }
//                if (expensesBySubmissionDate.isEmpty()) {
//                    ctx.result("Expenses of this submission date not found");
//                    ctx.status(404);
//                    logger.info("Not here not now");
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesBySubmissionDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Right here right now");
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("And the vision that was planted in my brain");
//        }
//    };

//    public Handler getExpensesBySubmissionDatePerEmployeePerManagerPerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int managerId = Integer.parseInt(ctx.pathParam("managerid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));
//
//            Set<Expenses> expenseByEmployeeId = null;
//            Set<Expenses> expenseByManagerId = this.ExpensesByManagerIdPerDirector(directorId, managerId);
//            Set<Expenses> expensesBySubmissionDate = new HashSet<>();
//
//            if (expenseByManagerId.isEmpty()) {
//                ctx.result("Expenses for this manager not found");
//                ctx.status(404);
//            } else {
//                expenseByEmployeeId = this.ExpensesByEmployeeIdPerManager(employeeId, managerId);
//            }
//
//            if (expenseByEmployeeId.isEmpty()) {
//                ctx.result("Expenses not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expenseByEmployeeId) {
//                    if (submitDate == expense.getDateSubmitted()) {
//                        expensesBySubmissionDate.add(expense);
//                    }
//                }
//                if (expensesBySubmissionDate.isEmpty()) {
//                    ctx.result("Expenses of this submission date not found");
//                    ctx.status(404);
//                    logger.info("Nothing to be seen here");
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesBySubmissionDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Well hello");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Still remains");
//        }
//    };
//
//    public Handler getExpensesBySubmissionDatePerEmployeePerDirector  = (ctx) -> {
//        String jwt = ctx.header("Authorization");
//        DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);
//
//        if(decodedJWT.getClaim("role").asString().equals("Director")) {
//            int directorId = Integer.parseInt(ctx.pathParam("directorid"));
//            int employeeId = Integer.parseInt(ctx.pathParam("employeeid"));
//            int submitDate = Integer.parseInt(ctx.pathParam("submitdate"));
//
//            Set<Expenses> expensesbyDirectorId = this.ExpensesByEmployeeIdPerDirector(directorId, employeeId);
//            Set<Expenses> expensesBySubmissionDate = new HashSet<>();
//
//            if (expensesbyDirectorId.isEmpty()) {
//                ctx.result("Expenses for this director not found");
//                ctx.status(404);
//            } else {
//                for (Expenses expense : expensesbyDirectorId) {
//                    if (submitDate == expense.getDateSubmitted()) {
//                        expensesBySubmissionDate.add(expense);
//                    }
//                }
//                if (expensesBySubmissionDate.isEmpty()) {
//                    ctx.result("Expenses of this submission date not found");
//                    ctx.status(404);
//                    logger.info("Stop trying to find me");
//
//                } else {
//                    Gson gson = new Gson();
//                    String expenseJSON = gson.toJson(expensesBySubmissionDate);
//                    ctx.result(expenseJSON);
//                    ctx.status(200);
//                    logger.info("Hello old friend");
//
//                }
//            }
//        }else {
//            ctx.status(403);
//            ctx.result("Improper authentication");
//            logger.info("Within the sound of silence");
//        }
//    };

    public Handler updateExpensesHandler = (ctx) ->{
        int expenseId = Integer.parseInt(ctx.pathParam("expenseid"));

        String body = ctx.body();
        Gson gson = new Gson();
        Expenses expense = gson.fromJson(body, Expenses.class);

        String reason = expense.getReason();
        String status = expense.getStatus();
        int statusDate = expense.getStatusDate();
        String managerNotes = expense.getManagerNotes();
        try {
            //updating expense reason
            String jwt = ctx.header("Authorization");
            DecodedJWT decodedJWT = JWTUtil.isValidJWT(jwt);

            //if(decodedJWT.getClaim().asBoolean()){
            this.expensesService.updateExpenseReason(this.expenseDAO.getExpenseById(expenseId), reason);

            //updating expense status
            if(decodedJWT.getClaim("role").asString().equals("Manager")) {
                this.expensesService.updateStatus(this.expenseDAO.getExpenseById(expenseId), status);
            }else {
                ctx.status(403);
                ctx.result("Improper authentication");
                logger.info("Unauthorized to update expense status");
            }

            //updating expense status date
            if(decodedJWT.getClaim("role").asString().equals("Manager")) {
                this.expensesService.updateStatusDate(this.expenseDAO.getExpenseById(expenseId), statusDate);
            }else {
                ctx.status(403);
                ctx.result("Improper authentication");
                logger.info("Unauthorized to update expense status date");
            }

            //updating expense notes
            if(decodedJWT.getClaim("role").asString().equals("Manager")) {
                this.expensesService.updateManagerNotes(this.expenseDAO.getExpenseById(expenseId), managerNotes);
            }else {
                ctx.status(403);
                ctx.result("Improper authentication");
                logger.info("Unauthorized to update expense manager notes");
            }

            Gson gson2 = new Gson();
            String expenseJSON = gson2.toJson(this.expenseDAO.getExpenseById(expenseId));
            ctx.result(expenseJSON);
            ctx.status(200);

            logger.info("This expense was successfully updated.");

        }catch(Exception e){
            ctx.result("Sorry! could not update!");
            ctx.status(404);
            logger.error(e);
        }


    };

    public Handler deleteExpenseHandler = (ctx) -> {
        int expenseId = Integer.parseInt(ctx.pathParam("expenseid"));
        if (this.expenseDAO.getExpenseById(expenseId).getStatus().equals("Pending")) {
            boolean deleted = this.expensesService.deleteExpenseById(expenseId);
            if (deleted) {
                ctx.result("The expense was deleted");
                ctx.status(200);
                logger.info("Expense " + expenseId +" deleted");
            } else {
                ctx.result("Sorry! could not delete");
                ctx.status(404);
                logger.info("Expense " + expenseId +" was not deleted");
            }
        }else{
            ctx.result("Expense can no longer be deleted since it has been approved or denied");
            ctx.status(403);
            logger.info("Expense " + expenseId +" was not deleted since it has been approved or denied");


        }

    };
}


