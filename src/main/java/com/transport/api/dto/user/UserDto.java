package com.transport.api.dto.user;

import com.transport.api.dto.PaymentDto;
import com.transport.api.dto.TransportationDto;
import com.transport.api.dto.user.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    private String password;
    @NotBlank
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    private Integer rating;
    private Boolean enabled;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    private String about;
    private List<TransportationDto> transportations;
    private List<PaymentDto> payments;
    private Set<RoleDto> roles;
}
