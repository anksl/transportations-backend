package com.transport.api.dto.user;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class RegistrationUserDto {

    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?!\\s).*[^\\s]$", message = "Не должно быть пробелов до или после имени")
    private String name;
    @NotBlank
    @Size(min = 1)
    @Pattern(regexp = "^(?!\\s).*[^\\s]$", message = "Не должно быть пробелов до или после пароля")
    private String password;
    @NotBlank
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    private String about;
    private Set<RoleDto> roles;

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : null;
    }
}