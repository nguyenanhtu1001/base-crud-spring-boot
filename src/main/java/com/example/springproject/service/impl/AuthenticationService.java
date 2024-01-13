package com.example.springproject.service.impl;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.AuthenticationRequest;
import com.example.springproject.dto.response.AuthenticationResponse;
import com.example.springproject.entity.Role;
import com.example.springproject.entity.User;
import com.example.springproject.repository.UserRepository;
import com.example.springproject.security.CustomUserDetail;
import com.example.springproject.utils.DateUtils;
import com.example.springproject.utils.MapperUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthenticationService {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;


  public ResponseGeneral<AuthenticationResponse> register(AuthenticationRequest dto) {
    User user = MapperUtils.toEntity(dto, User.class);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setCreatedAt(DateUtils.getCurrentTimeMillis());
    user.setRole(Role.USER);
    userRepository.save(user);
    return ResponseGeneral.ofCreated("Success!", AuthenticationResponse.builder()
          .token(jwtService.generateToken(new CustomUserDetail(user)))
          .build());
  }

  public ResponseGeneral<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
    var token = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    CustomUserDetail customUserDetail = (CustomUserDetail) authenticationManager.authenticate(token).getPrincipal();
    return ResponseGeneral.ofSuccess("User has been authenticated!", AuthenticationResponse.builder()
          .token(jwtService.generateToken(customUserDetail))
          .build());
  }



  @PostConstruct
  @Transactional
  void createAdmin(){
    userRepository.removeAdmin();
    User admin = User.builder().
            username("admin").
            password(passwordEncoder.encode("123456")).
            role(Role.ADMIN).
            build();
    userRepository.save(admin);
  }

  @PreDestroy
  @Transactional
  void deleteAdmin(){
    userRepository.removeAdmin();
  }
}
