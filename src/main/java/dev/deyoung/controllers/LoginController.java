package dev.deyoung.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import dev.deyoung.Utils.JWTUtil;
import dev.deyoung.daos.EmployeeDAO;
import dev.deyoung.daos.EmployeeDaoHibernate;
import dev.deyoung.daos.ManagerDAO;
import dev.deyoung.daos.ManagerDaoHibernate;
import dev.deyoung.entities.Employees;
import dev.deyoung.entities.Login;
import dev.deyoung.entities.Managers;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;


import java.util.Set;

public class LoginController {

    ManagerDAO managerDAO = new ManagerDaoHibernate();
    EmployeeDAO employeeDAO = new EmployeeDaoHibernate();
    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());


    public Handler loginHandler = (ctx) -> {

        String body = ctx.body();
        Gson gson = new Gson();
        Login login = gson.fromJson(body, Login.class);
        Set<Managers> allManagers = this.managerDAO.getManagers();
        Set<Employees> allEmployees = this.employeeDAO.getEmployees();

        try{
            String username = login.getUsername();

            for(Managers manager : allManagers){
                if(username.equals(manager.getUsername())){
                    String jwtManager = JWTUtil.createManagerToken("Manager", manager);
                    ctx.result(jwtManager);
                    logger.info("Created manager JWT");
                }else{
                    for(Employees employee : allEmployees) {
                        String jwtEmployee = JWTUtil.generate("Employee", employee);
                        ctx.result(jwtEmployee);
                        logger.info("Created employee JWT");

                    }
                }
            }

        }catch(JWTVerificationException e){
            ctx.result("Username not found!");
            logger.error(e);
        }
    };
}
