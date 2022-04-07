package service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.employeemanagementwebapp.model.User;

import dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}