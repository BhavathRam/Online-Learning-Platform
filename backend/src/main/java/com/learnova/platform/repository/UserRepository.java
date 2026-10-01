package com.learnova.platform.repository;
import com.learnova.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<User,Long>{Optional<User> findByEmailIgnoreCase(String email);boolean existsByEmailIgnoreCase(String email);List<User> findByRole(com.learnova.platform.entity.Role role);long countByRole(com.learnova.platform.entity.Role role);}
