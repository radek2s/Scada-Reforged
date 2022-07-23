package org.reggster.srfcore.security.jwt

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTAuthorizationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtTokenUtils: JWTTokenUtils
) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if(header == null || !header.startsWith("Bearer:")) {
            chain.doFilter(request, response)
            return
        }
        val token: String = header.split(":")[1].trim()
        if(!jwtTokenUtils.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        val user = userDetailsService
            .loadUserByUsername(jwtTokenUtils.getUsername(token))

        UsernamePasswordAuthenticationToken(
            user, null, user.authorities)
            .apply {
                details = WebAuthenticationDetailsSource().buildDetails(request)
            }
            .also {
                SecurityContextHolder.getContext().authentication = it
            }

        chain.doFilter(request, response)
    }
    //https://blog.iamprafful.com/spring-boot-rest-api-authentication-best-practices-using-jwt-2022
}