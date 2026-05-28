package com.fitnessmanage.system.security;

import com.fitnessmanage.system.user.User;
import com.fitnessmanage.system.user.UserRepository;
import com.fitnessmanage.system.user.Role;
import com.fitnessmanage.system.user.dto.AuthResponse;
import com.fitnessmanage.system.user.dto.LoginRequest;
import com.fitnessmanage.system.user.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Неверный email или пароль"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new RuntimeException("Неверный email или пароль");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}
