package com.learnova.platform.config;
import com.learnova.platform.entity.*;
import com.learnova.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration public class AdminInitializer {
 @Bean CommandLineRunner createAdmin(UserRepository users,PasswordEncoder encoder,@Value("${app.admin.email}") String email,@Value("${app.admin.password}") String password){return args->{if(password==null||password.length()<12)throw new IllegalStateException("Set ADMIN_PASSWORD to a unique value with at least 12 characters before starting the API.");if(!users.existsByEmailIgnoreCase(email))users.save(new User("Platform Admin",email,encoder.encode(password),Role.ADMIN));};}
}
