package org.reggster.srfcore.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTTokenUtils(
    private val jwtSecret: String = "zdtlD3JK56m6wTTgsNFhqzjqP",
    private val jwtIssuer: String = "example.io",
    val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)
) {

    fun generateAccessToken(username: String) =
        JWT.create()
            .withIssuer(jwtIssuer)
            .withSubject(username)
            .sign(algorithm)

    fun getUsername(token: String) =
        JWT.decode(token).subject

    fun getExpirationDate(token: String) =
        Date()

    fun validate(token: String) =
        try {
            val jwt: DecodedJWT = JWT.decode(token)
            //ToDo: Add validation
            true
        } catch (e: Exception) {
            false
        }
}