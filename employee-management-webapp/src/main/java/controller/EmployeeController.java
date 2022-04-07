package controller;


import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.employeemanagementwebapp.model.Employee;
import com.example.employeemanagementwebapp.model.Role;
import com.example.employeemanagementwebapp.model.User;
import com.example.employeemanagementwebapp.repository.UserRepository;

import Payloads.ApiResponse;
import Payloads.JwtAuthenticationResponse;
import Payloads.LoginRequest;
import Payloads.SignUpRequest;
import security.JwtTokenProvider;
import service.EmployeeService;

@RestController
@RequestMapping("/api/auth")

public class EmployeeController {
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    

	@Autowired
	private EmployeeService employeeService;
	
//	 @PostMapping("/signin")
//	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//	        Authentication authentication = authenticationManager.authenticate(
//	                new UsernamePasswordAuthenticationToken(
//	                        loginRequest.getUsernameOrEmail(),
//	                        loginRequest.getPassword()
//	                )
//	        );
//
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//	        String jwt = tokenProvider.generateToken(authentication);
//	        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//	    }

	    @PostMapping("/signup")
	    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
	        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
	            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
	                    HttpStatus.BAD_REQUEST);
	        }

	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
	                    HttpStatus.BAD_REQUEST);
	        }

	        // Creating user's account
	        User user = new User(signUpRequest.getFirst_name(),signUpRequest.getLast_name(), signUpRequest.getUsername(),
	                signUpRequest.getEmail(), signUpRequest.getPassword());

	        user.setPassword(passwordEncoder.encode(user.getPassword()));
 
 

	        User result = userRepository.save(user);

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/api/users/{username}")
	                .buildAndExpand(result.getUsername()).toUri();

	        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	    }
	}