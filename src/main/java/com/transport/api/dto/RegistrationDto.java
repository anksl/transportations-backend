package com.transport.api.dto;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    @NotBlank
    private Set<RoleDto> roles;
}