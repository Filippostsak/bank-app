package com.microservices.userservice.controller;

import com.microservices.userservice.dto.LoginDTO;
import com.microservices.userservice.dto.LoginResponseDTO;
import com.microservices.userservice.model.User;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.service.TokenBlacklistService;
import com.microservices.userservice.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final TokenBlacklistService tokenBlacklistService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        // Fetch the user and set isActive to true
        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(true);
            userRepository.save(user);
        }

        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = tokenProvider.getUsernameFromToken(jwt);

        // Fetch the user and set isActive to false
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(false);
            userRepository.save(user);
        }

        tokenBlacklistService.blacklistToken(jwt);
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
