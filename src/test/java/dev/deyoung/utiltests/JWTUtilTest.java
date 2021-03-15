package dev.deyoung.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.deyoung.Utils.JWTUtil;
import dev.deyoung.daos.EmployeeDAO;
import dev.deyoung.daos.EmployeeDaoHibernate;
import dev.deyoung.entities.Employees;
import dev.deyoung.entities.Managers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class JWTUtilTest {

    Employees employee = new Employees(0, "Aaron", "Samuels", "The Jocks", 1, "aaronsamuels", "grool");
    Managers manager = new Managers(0, "Amber", "DAlessio", "Hot Dogs", "amberdalessio", "thatwasonetime");

    @Test
    @Order(1)
    void creates_jwt_employee(){
        String jwt = JWTUtil.generate("Employee", employee);
        System.out.println(jwt);
    }

    @Test
    @Order(2)
    void creates_jwt_manager(){
        String jwt = JWTUtil.createManagerToken("Manager", manager);
        System.out.println(jwt);
    }


    @Test
    @Order(3)
    void decode_jwt(){

        DecodedJWT jwt = JWTUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmaXJzdE5hbWUiOiJLYXJlbiIsImxhc3ROYW1lIjoiU21pdGgiLCJyb2xlIjoiRW1wbG95ZWUiLCJpZCI6MX0.kDjY5dMgilaJec0GwJf4M1vNvpItCXS1QXf0QSn8Ock");
        String role = jwt.getClaim("role").asString();
        System.out.println(role);

    }


}
