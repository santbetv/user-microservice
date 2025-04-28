package com.globallogic.usermicroservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRequestDto {

    UUID id;
    String number;
    String citycode;
    String contrycode;
}
