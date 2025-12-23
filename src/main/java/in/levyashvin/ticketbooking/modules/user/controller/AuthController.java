package in.levyashvin.ticketbooking.modules.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.levyashvin.ticketbooking.modules.user.dto.AuthenticationRequest;
import in.levyashvin.ticketbooking.modules.user.dto.AuthenticationResponse;
import in.levyashvin.ticketbooking.modules.user.dto.RegisterRequest;
import in.levyashvin.ticketbooking.modules.user.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
        @RequestBody AuthenticationRequest request
    ) {        
        return ResponseEntity.ok(service.authenticate(request));
    }

}
