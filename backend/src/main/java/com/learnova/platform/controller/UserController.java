package com.learnova.platform.controller;
import com.learnova.platform.dto.AuthDtos.UserResponse;
import com.learnova.platform.dto.AuthDtos.ProfileUpdate;
import com.learnova.platform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/users") public class UserController {
 private final UserService users;public UserController(UserService u){users=u;}
 @GetMapping("/me") UserResponse profile(){return users.profile();}
 @PutMapping("/me") UserResponse update(@Valid @RequestBody ProfileUpdate r){return users.update(r);}
 @PreAuthorize("hasRole('ADMIN')") @GetMapping List<Map<String,Object>> students(){return users.students();}
}
