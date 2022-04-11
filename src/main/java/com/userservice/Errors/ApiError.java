package com.userservice.Errors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {

    private String code;
    private String message;
}
