package com.globallogic.usermicroservice.application.service.impl;

import com.globallogic.usermicroservice.Infrastructure.common.configuration.JwtUtil;
import com.globallogic.usermicroservice.domain.dto.PhoneRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserResponseDto;
import com.globallogic.usermicroservice.domain.entity.User;
import com.globallogic.usermicroservice.domain.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) // ✅ solo extender SpringExtension para usar Mockito
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    //@InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    @DisplayName("Registrar usuario exitosamente")
    void validRequestShouldCreateUserSuccessfully() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        PhoneRequestDto phone = PhoneRequestDto.builder()
                .id(uuid)
                .citycode("+57")
                .number("3165106361")
                .contrycode("57")
                .build();

        UserRequestDto requestDto = new UserRequestDto(
                "Juan Perez",
                "juanperez@example.com",
                "Passw0rd1",
                List.of(phone)
        );

        User userSaved = User.builder()
                .id(UUID.randomUUID())
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password("encrypted-password")
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(List.of())
                .build();

        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encrypted-password");
        when(userRepository.save(any(User.class))).thenReturn(userSaved);
        when(jwtUtil.createFromUser(any(User.class))).thenReturn("fake-jwt-token");

        // Act
        UserResponseDto response = userService.register(requestDto);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("fake-jwt-token");
        assertThat(response.isActive()).isTrue();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Registrar usuario duplicado")
    void duplicateEmailShouldThrowRuntimeException() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        PhoneRequestDto phone = PhoneRequestDto.builder()
                .id(uuid)
                .citycode("+57")
                .number("3165106361")
                .contrycode("57")
                .build();

        UserRequestDto requestDto = new UserRequestDto(
                "Juan Perez",
                "juanperez@example.com",
                "Passw0rd1",
                List.of(phone)
        );

        when(userRepository.findByEmail(requestDto.getEmail()))
                .thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThatThrownBy(() -> userService.register(requestDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("El correo ya está registrado.");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Validar formato de contraseña inválida usando Bean Validation")
    void invalidPasswordFormatShouldFailValidation() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        PhoneRequestDto phone = PhoneRequestDto.builder()
                .id(uuid)
                .citycode("+57")
                .number("3165106361")
                .contrycode("57")
                .build();

        UserRequestDto requestDto = new UserRequestDto(
                "Pedro Ramirez",
                "pedro@example.com",
                "password", // ❌ Contraseña inválida
                List.of(phone)
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Act
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(requestDto);

        // Assert
        assertThat(violations)
                .isNotEmpty()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password") &&
                        v.getMessage().contains("La contraseña debe tener 1 mayúscula"));
    }

}