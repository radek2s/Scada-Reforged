package org.reggster.srfcore.security

import org.reggster.srfcore.security.jwt.JWTTokenUtils
import org.reggster.srfcore.security.acl.ScadaUserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
@RequestMapping("/api/v1/")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val scadaUserService: ScadaUserService,
    private val jwtTokenUtils: JWTTokenUtils
) {

    @PostMapping("login")
    fun login(@RequestParam(value = "username") username: String, @RequestParam(value = "password") password: String): ResponseEntity<String> {
        return try {
            val auth:Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(username, password)
            )
            val user: User = auth.principal as User
            ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtils.generateAccessToken(username)).body("HI")
        } catch (e: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}