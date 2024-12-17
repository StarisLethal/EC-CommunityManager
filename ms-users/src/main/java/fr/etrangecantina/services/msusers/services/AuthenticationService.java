package fr.etrangecantina.services.msusers.services;

import fr.etrangecantina.services.msusers.DTO.RegistrationRequest;
import fr.etrangecantina.services.msusers.DTO.UserDetailsDTO;
import fr.etrangecantina.services.msusers.model.Token;
import fr.etrangecantina.services.msusers.model.User;
import fr.etrangecantina.services.msusers.repositories.RoleRepository;
import fr.etrangecantina.services.msusers.repositories.TokenRepository;
import fr.etrangecantina.services.msusers.repositories.UserRepository;
import fr.etrangecantina.services.msusers.DTO.AuthenticationRequest;
import fr.etrangecantina.services.msusers.security.AuthenticationResponse;
import fr.etrangecantina.services.msusers.security.JwtService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public void register(@Valid RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role User was not initialized"));

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var claims = new HashMap<String, Object>();
            var jwtToken = jwtService.generateToken(claims, (UserDetailsDTO) auth.getPrincipal());
            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch (Exception e) {
            System.err.println("Error during authentication: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    @Transactional
    public void activateAccount(String token) {
        //TODO Method for activate account by admin
    }
}
