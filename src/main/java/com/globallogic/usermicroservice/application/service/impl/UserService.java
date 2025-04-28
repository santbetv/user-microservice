package com.globallogic.usermicroservice.application.service.impl;

import com.globallogic.usermicroservice.Infrastructure.common.configuration.JwtUtil;
import com.globallogic.usermicroservice.domain.dto.PhoneRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserLoginResponseDto;
import com.globallogic.usermicroservice.domain.dto.UserRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserResponseDto;
import com.globallogic.usermicroservice.domain.entity.Phone;
import com.globallogic.usermicroservice.domain.entity.User;
import com.globallogic.usermicroservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponseDto register(UserRequestDto request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("El correo ya está registrado.");
                });

        User usuario = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(mapPhones(request.getPhones()))
                .build();

        usuario = userRepository.save(usuario);

        String token = jwtUtil.createFromUser(usuario);

        return UserResponseDto.builder()
                .id(usuario.getId())
                .created(usuario.getCreated())
                .lastLogin(usuario.getLastLogin())
                .token(token)
                .isActive(usuario.isActive())
                .build();
    }

    private List<Phone> mapPhones(List<PhoneRequestDto> phoneDtos) {
        return Optional.ofNullable(phoneDtos)
                .orElse(List.of())
                .stream()
                .map(phoneDto -> Phone.builder()
                        .id(phoneDto.getId())
                        .cityCode(phoneDto.getCitycode())
                        .countryCode(phoneDto.getContrycode())
                        .number(phoneDto.getNumber())
                        .build())
                .collect(Collectors.toList());
    }


    public UserLoginResponseDto login(String token) {
        // Validar el token y extraer el email (subject)
        String email = jwtUtil.getEmailFromToken(token);

        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar el lastLogin
        usuario.setLastLogin(LocalDateTime.now());
        userRepository.save(usuario);

        // Generar nuevo token
        String newToken = jwtUtil.createFromUser(usuario);

        return UserLoginResponseDto.builder()
                .id(usuario.getId())
                .created(usuario.getCreated())
                .lastLogin(usuario.getLastLogin())
                .token(newToken)
                .isActive(usuario.isActive())
                .name(usuario.getName())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .phones(usuario.getPhones().stream()
                        .map(p -> new PhoneRequestDto(p.getId(), p.getNumber(), p.getCityCode(), p.getCountryCode()))
                        .collect(Collectors.toList()))
                .build();
    }


}
