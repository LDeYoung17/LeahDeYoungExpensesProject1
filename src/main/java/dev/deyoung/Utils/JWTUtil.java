package dev.deyoung.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.deyoung.controllers.ExpenseController;
import org.apache.log4j.Logger;

public class JWTUtil {
    private static final String secret = "Secrets don't make friends, but friends make secrets!";
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);
    private static Logger logger = Logger.getLogger(ExpenseController.class.getName());


    public static String generate(String role, String employeeName) {

        String token = JWT.create()
                .withClaim("role", role)
                .withClaim("empName", employeeName)
                .sign(algorithm);
        logger.info("Created JWT in util.");
        return token;
    }

    public static DecodedJWT isValidJWT(String token){
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            jwt.getClaim("role");
            logger.info("JWT in util is valid.");
            return jwt;
        }catch(JWTVerificationException e){
            e.printStackTrace();
            logger.error(e);
            return null;
        }
    }
}
