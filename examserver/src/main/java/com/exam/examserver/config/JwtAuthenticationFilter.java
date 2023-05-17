package com.exam.examserver.config;

import com.exam.examserver.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            System.out.println("jwt token "+jwtToken);
            try {
                username = this.jwtUtil.extractUsername(jwtToken);
                System.out.println("username "+username);
            }
            catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println("Token expired");
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error");
            }

        }
        else
            System.out.println("Invalid Token !!");

        // validating
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("User Details " +userDetails);
            if (this.jwtUtil.validateToken(jwtToken,userDetails)) {
                // token is valid
                System.out.println("Is valid token");

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                System.out.println("username and password"+usernamePasswordAuthenticationToken);
            }
            else {
                System.out.println("Token is not valid!!");
            }
        }
        filterChain.doFilter(request,response);
    }
}
