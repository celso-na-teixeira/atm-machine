package com.nas.atm_machine.auth.config;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.repository.AccountRepository;
import com.nas.atm_machine.account.service.AccountService;
import com.nas.atm_machine.auth.AccountAuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;
    private final AccountAuthService accountAuthService;

    public JwtTokenFilter(JwtUtil jwtUtil, AccountRepository accountRepository, AccountAuthService accountAuthService) {
        this.jwtUtil = jwtUtil;
        this.accountRepository = accountRepository;
        this.accountAuthService = accountAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String accountNumber = null;

        // Extract JWT from Authorization Header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                accountNumber = jwtUtil.extractUsername(token);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
                return;
            }
        }

        // Authenticate User if Token is Valid
        if (accountNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = accountAuthService.loadUserByUsername(accountNumber);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
