package com.example.springproject.controller;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.AuthenticationRequest;
import com.example.springproject.dto.response.AuthenticationResponse;
import com.example.springproject.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseGeneral<AuthenticationResponse> register(@Validated @RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.register(authenticationRequest);
    }


    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseGeneral<AuthenticationResponse> authenticated(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.authenticate(authenticationRequest);
    }
}
