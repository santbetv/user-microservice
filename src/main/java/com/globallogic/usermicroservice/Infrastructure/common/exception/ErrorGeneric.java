package com.globallogic.usermicroservice.Infrastructure.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorGeneric {
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;
}
