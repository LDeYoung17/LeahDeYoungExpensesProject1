package dev.deyoung.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.deyoung.Utils.JWTUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class JWTUtilTest {

    @Test
    @Order(1)
    void creates_jwt(){
        String jwt = JWTUtil.generate("Manager", "Jack Harkness");
        System.out.println(jwt);
    }

    @Test
    @Order(2)
    void decode_jwt(){

        DecodedJWT jwt = JWTUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTWFuYWdlciIsImVtcE5hbWUiOiJKYWNrIEhhcmtuZXNzIn0.MGAKqLh1Cg03lmKvUBgB2U0oWwiksQF0K17ayrf2igQ");
        String role = jwt.getClaim("role").asString();
        System.out.println(role);

    }


}
