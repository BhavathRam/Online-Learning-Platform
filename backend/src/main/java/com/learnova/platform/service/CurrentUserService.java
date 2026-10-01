package com.learnova.platform.service;
import com.learnova.platform.entity.User;
import com.learnova.platform.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service public class CurrentUserService {
 private final UserRepository users; public CurrentUserService(UserRepository users){this.users=users;}
 public User get(){Object p=SecurityContextHolder.getContext().getAuthentication().getPrincipal();try{return users.findById(Long.parseLong(p.toString())).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Account no longer exists"));}catch(NumberFormatException e){throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid authentication");}}
}
