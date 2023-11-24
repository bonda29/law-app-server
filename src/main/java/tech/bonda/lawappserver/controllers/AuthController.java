package tech.bonda.lawappserver.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.lawappserver.AuthService.AuthService;
import tech.bonda.lawappserver.payload.request.LoginPayload;
import tech.bonda.lawappserver.payload.request.RegisterPayload;


@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterPayload data) {
        return authService.registerUser(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginPayload data) {
        return authService.login(data);
    }
}
