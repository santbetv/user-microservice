package com.globallogic.usermicroservice.Infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.usermicroservice.Infrastructure.common.exception.ErrorGeneric;
import com.globallogic.usermicroservice.Infrastructure.common.exception.ErrorResponse;
import com.globallogic.usermicroservice.application.service.impl.UserService;
import com.globallogic.usermicroservice.domain.dto.PhoneRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserRequestDto;
import com.globallogic.usermicroservice.domain.dto.UserResponseDto;
import com.globallogic.usermicroservice.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    private static PhoneRequestDto getSamplePhone() {
        return PhoneRequestDto.builder()
                .id(UUID.randomUUID())
                .citycode("+57")
                .number("3165106361")
                .contrycode("57")
                .build();
    }

    @Test
    void signUpValidRequestShouldReturn201() throws Exception {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto(
                "Juan Perez",
                "juanperez@example.com",
                "Passw0rd1",
                List.of(getSamplePhone())
        );

        UserResponseDto responseDto = UserResponseDto.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("fake-jwt-token")
                .isActive(true)
                .build();

        when(userService.register(any(UserRequestDto.class))).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void signUpInvalidPasswordShouldReturn400() throws Exception {
        // Arrange
        UserRequestDto requestDto = new UserRequestDto(
                "Pedro Ramirez",
                "pedro@example.com",
                "password",
                List.of(getSamplePhone())
        );

        // Act
        var result = mockMvc.perform(post("/api/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        System.out.println("Respuesta JSON: " + jsonResponse);

        ErrorResponse errorResponse = objectMapper.readValue(jsonResponse, ErrorResponse.class);

        // Assertions
        assertEquals(400, errorResponse.getError().get(0).getCodigo());
        assertTrue(errorResponse.getError().get(0).getDetail()
                .contains("La contraseña debe tener 1 mayúscula, exactamente 2 números, y entre 8 y 12 caracteres."));
    }
}