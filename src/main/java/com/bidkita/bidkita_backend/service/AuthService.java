package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.request.LoginRequestDTO;
import com.bidkita.bidkita_backend.dto.request.RegisterRequestDTO;
import com.bidkita.bidkita_backend.dto.response.LoginResponseDTO;
import com.bidkita.bidkita_backend.exception.EmailAlreadyExistsException;
import com.bidkita.bidkita_backend.exception.InvalidCredentialsException;
import com.bidkita.bidkita_backend.exception.ResourceNotFoundException;
import com.bidkita.bidkita_backend.model.Buyer;
import com.bidkita.bidkita_backend.model.Seller;
import com.bidkita.bidkita_backend.model.User;
import com.bidkita.bidkita_backend.repository.UserRepository;
import com.bidkita.bidkita_backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username sudah digunakan");
        }

        User user;
        String role = dto.getRole() == null ? "" : dto.getRole().toUpperCase();
        if ("SELLER".equals(role)) {
            user = new Seller(dto.getUsername(), dto.getEmail(),
                    passwordEncoder.encode(dto.getPassword()), dto.getPhone());
        } else {
            user = new Buyer(dto.getUsername(), dto.getEmail(),
                    passwordEncoder.encode(dto.getPassword()), dto.getPhone());
        }
        userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());
        return new LoginResponseDTO(token, user.getUserId(), user.getRole(), user.getUsername());
    }

    public User getMe(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));
    }
}
