package tech.bonda.lawappserver.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.lawappserver.models.Role.ERole;
import tech.bonda.lawappserver.models.Role.Role;
import tech.bonda.lawappserver.models.User;
import tech.bonda.lawappserver.payload.request.LoginPayload;
import tech.bonda.lawappserver.payload.request.RegisterPayload;
import tech.bonda.lawappserver.payload.response.JwtResponse;
import tech.bonda.lawappserver.payload.response.MessageResponse;
import tech.bonda.lawappserver.repository.RoleRepository;
import tech.bonda.lawappserver.repository.UserRepository;
import tech.bonda.lawappserver.security.jwt.JwtUtils;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(RegisterPayload data) {
        if (userRepository.existsByUsername(data.getUsername()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }
        if (userRepository.existsByEmail(data.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        User user = User.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(encoder.encode(data.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> login(LoginPayload data) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse response = JwtResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();

        return ResponseEntity.ok(response);
    }
}
