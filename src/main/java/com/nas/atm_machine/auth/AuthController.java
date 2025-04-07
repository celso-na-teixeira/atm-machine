package com.nas.atm_machine.auth;

import com.nas.atm_machine.auth.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        log.info("Login");
       try {
           Authentication authentication = authManager.authenticate(
                   new UsernamePasswordAuthenticationToken(request.getAccountNumber(), request.getPin())
           );

           String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
           return ResponseEntity.ok(token);
       }catch (BadCredentialsException credentialsException) {
           log.error("Error:", credentialsException);
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(credentialsException.getMessage());
       }
    }
}
