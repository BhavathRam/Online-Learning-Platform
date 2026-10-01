package com.learnova.platform.service;
import com.learnova.platform.dto.AuthDtos.*;
import com.learnova.platform.entity.*;
import com.learnova.platform.repository.UserRepository;
import com.learnova.platform.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service public class AuthService {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthService(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 public AuthResponse register(RegisterRequest r){if(users.existsByEmailIgnoreCase(r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already registered");User u=users.save(new User(r.name().trim(),r.email(),encoder.encode(r.password()),Role.STUDENT));return response(u);}
 public AuthResponse login(LoginRequest r){User u=users.findByEmailIgnoreCase(r.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password is incorrect"));if(!encoder.matches(r.password(),u.getPassword()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password is incorrect");return response(u);}
 private AuthResponse response(User u){return new AuthResponse(jwt.generate(u),"Bearer",new UserResponse(u.getId(),u.getName(),u.getEmail(),u.getRole().name()));}
}
