package org.reggster.srfcore.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    private val authManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        return try {
            val mapper = jacksonObjectMapper()
            val creds = mapper.readValue<User>(request.inputStream)
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    creds.username,
                    creds.password,
                    ArrayList()
                )
            )
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val authClaims: MutableList<String> = mutableListOf()
        authResult.authorities?.let { authorities ->
            authorities.forEach { claim -> authClaims.add(claim.toString()) }
        }
        val token = JWT.create()
            .withIssuer("issuer")
            .withSubject(authResult.principal as String)
            .sign(Algorithm.HMAC256("1234"))
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer: $token")

    }
    //https://github.com/osahner/kotlin-spring-boot-rest-jpa-jwt-starter/blob/main/src/main/kotlin/osahner/security/JWTAuthenticationFilter.kt
}