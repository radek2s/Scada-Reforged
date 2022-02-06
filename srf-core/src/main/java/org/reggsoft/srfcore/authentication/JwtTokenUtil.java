package org.reggsoft.srfcore.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    //TODO: Modify the jwtSecret to be saved in external file.
    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String jwtIssuer = "example.io";

    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    //TODO: java-jwt token adaptation

    public String generateAccessToken(String username) {
        //TODO: Extend this function to genereate a token with expirationData and IssuedAt
        return JWT.create()
                .withIssuer(jwtIssuer)
                .withSubject(username)
                .sign(algorithm);
    }



    public String getUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return new Date();
    }

    public boolean validate(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            //TODO: Add validation of Token is it not out-dated etc...
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}