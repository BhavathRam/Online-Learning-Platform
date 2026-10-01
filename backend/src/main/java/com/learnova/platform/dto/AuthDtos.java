package com.learnova.platform.dto;
import jakarta.validation.constraints.*;
public final class AuthDtos { private AuthDtos(){}
 public record RegisterRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email @Size(max=190) String email,@NotBlank @Size(min=8,max=72) String password){}
 public record LoginRequest(@NotBlank @Email String email,@NotBlank String password){}
 public record UserResponse(Long id,String name,String email,String role){}
 public record AuthResponse(String token,String tokenType,UserResponse user){}
 public record ProfileUpdate(@NotBlank @Size(max=100) String name,@NotBlank @Email @Size(max=190) String email){}
}
