package com.learnova.platform.service;
import com.learnova.platform.dto.AuthDtos.*;
import com.learnova.platform.entity.*;
import com.learnova.platform.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service public class UserService {
 private final UserRepository users;private final EnrollmentRepository enrollments;private final CurrentUserService current;
 public UserService(UserRepository u,EnrollmentRepository e,CurrentUserService c){users=u;enrollments=e;current=c;}
 @Transactional(readOnly=true) public UserResponse profile(){return view(current.get());}
 @Transactional public UserResponse update(ProfileUpdate r){User u=current.get();if(users.findByEmailIgnoreCase(r.email()).filter(other->!other.getId().equals(u.getId())).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already in use");u.setName(r.name().trim());u.setEmail(r.email());return view(users.save(u));}
 @Transactional(readOnly=true) public List<Map<String,Object>> students(){return users.findByRole(Role.STUDENT).stream().map(u->{Map<String,Object> row=new LinkedHashMap<>();row.put("id",u.getId());row.put("name",u.getName());row.put("email",u.getEmail());row.put("createdAt",u.getCreatedAt());row.put("enrollmentCount",enrollments.countByUserId(u.getId()));return row;}).toList();}
 private UserResponse view(User u){return new UserResponse(u.getId(),u.getName(),u.getEmail(),u.getRole().name());}
}
