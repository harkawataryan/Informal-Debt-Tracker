package com.jankydebt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePersonRequest {
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
}
