package com.globallogic.usermicroservice.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    String name;

    @NotBlank(message = "El correo no puede ser vacío.")
    @Email(message = "Formato de correo inválido. Debe ser aaaa@dominio.com")
    String email;

    @NotBlank(message = "La contraseña no puede ser vacía.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=(?:.*\\d.*\\d)[^\\d]*$)[a-zA-Z\\d]{8,12}$",
            message = "La contraseña debe tener 1 mayúscula, exactamente 2 números, y entre 8 y 12 caracteres."
    )
    String password;

    List<PhoneRequestDto> phones;

}
