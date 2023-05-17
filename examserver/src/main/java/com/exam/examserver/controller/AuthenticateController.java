package com.exam.examserver.controller;

import com.exam.examserver.config.JwtUtils;
import com.exam.examserver.model.JwtRequest;
import com.exam.examserver.model.JwtResponse;
import com.exam.examserver.model.User;
import com.exam.examserver.service.impl.UserDetailsServiceImpl;
import com.exam.examserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken (@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        }
        catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("User not found !!");
        }

        // user is authenticated

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = this.jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate (String username,String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }
        catch (DisabledException e) {
            throw new Exception("USER DISABLED " + e.getMessage());
        }
        catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials " + e.getMessage());
        }
    }

    // return the detail of current user

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        System.out.println("principal"+principal.getName());
        return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));
    }

}
