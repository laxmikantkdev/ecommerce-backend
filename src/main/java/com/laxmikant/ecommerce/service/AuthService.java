package com.laxmikant.ecommerce.service;

import com.laxmikant.ecommerce.dto.JwtResponse;
import com.laxmikant.ecommerce.dto.LoginRequest;
import com.laxmikant.ecommerce.dto.RegisterRequest;
import com.laxmikant.ecommerce.entity.User;
import com.laxmikant.ecommerce.repository.UserRepository;
import com.laxmikant.ecommerce.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest request, boolean admin) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(admin ? User.Role.ROLE_ADMIN : User.Role.ROLE_USER);
        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);

        String token = jwtUtil.generateToken(request.getEmail());
        return new JwtResponse(token);
    }
}
