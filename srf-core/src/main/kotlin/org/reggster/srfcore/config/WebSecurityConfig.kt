package org.reggster.srfcore.config

import org.reggster.srfcore.security.jwt.JWTAuthorizationFilter
import org.reggster.srfcore.security.acl.ScadaUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    val userDetailsService: ScadaUserService,
    private val jwtAuthorizationFilter: JWTAuthorizationFilter,
): WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder()

    @Bean
    fun authProvider(): DaoAuthenticationProvider =
        DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder())
        }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider())
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/login/**").permitAll()
            .antMatchers("/srf-ws/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .headers().frameOptions().disable()
            .and()
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/srf-ws/**")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also { cors ->
        CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization", "Content-Disposition"
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }
}