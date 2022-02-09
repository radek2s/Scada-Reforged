package org.reggsoft.srfcore.authentication;

import org.reggsoft.srfcore.persistance.dao.ScadaUserRepository;
import org.reggsoft.srfcore.persistance.entity.ScadaUser;
import org.reggsoft.srfcore.services.ScadaUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping(path = "api")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final ScadaUserService userService;


    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil, ScadaUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user.getUsername())).body("Hi");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("create")
    public ResponseEntity<UserDetails> createUser(@RequestBody ScadaUser user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }


}
