package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.request.LoginRequestDTO;
import com.bidkita.bidkita_backend.dto.request.RegisterRequestDTO;
import com.bidkita.bidkita_backend.dto.response.LoginResponseDTO;
import com.bidkita.bidkita_backend.dto.response.UserResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;

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

        String role = dto.getRole() == null ? "" : dto.getRole().toUpperCase();
        if (!"BUYER".equals(role) && !"SELLER".equals(role)) {
            throw new IllegalArgumentException("Role harus BUYER atau SELLER");
        }

        User user;
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

    public UserResponseDTO getMe(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User tidak ditemukan"));
        return toUserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserDTO)
                .toList();
    }

    private UserResponseDTO toUserDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setRegisteredAt(user.getRegisteredAt().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
        return dto;
    }
}
