package com.marco.bankapp.auth.service;

import com.marco.bankapp.auth.dto.request.RegisterRequest;
import com.marco.bankapp.auth.dto.response.RegisterResponse;
import com.marco.bankapp.auth.entity.AppUser;
import com.marco.bankapp.auth.enums.Role;
import com.marco.bankapp.auth.enums.UserStatus;
import com.marco.bankapp.auth.mapper.AuthMapper;
import com.marco.bankapp.auth.repository.AppUserRepository;
import com.marco.bankapp.common.exception.ConflictException;
import com.marco.bankapp.customer.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final AuthMapper authMapper;

    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (appUserRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(
                    "EMAIL_ALREADY_EXISTS",
                    "Email is already registered"
            );
        }

        Instant now = Instant.now();

        String passwordHash = passwordEncoder.encode(
                registerRequest.getPassword()
        );

        AppUser appUser = AppUser.builder()
                .id(UUID.randomUUID())
                .email(registerRequest.getEmail())
                .passwordHash(passwordHash)
                .status(UserStatus.ACTIVE)
                .roles(Set.of(Role.CLIENTE))
                .createdAt(now)
                .updatedAt(now)
                .build();

        AppUser savedUser = appUserRepository.save(appUser);

        customerService.createForRegistration(
                savedUser.getId(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getPhone(),
                registerRequest.getDateOfBirth()
        );

        return authMapper.toRegisterResponse(savedUser);
        
    }

}
