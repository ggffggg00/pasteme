package ru.borisof.pasteme.account.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private String firstname;

    private String lastname;

    @NotBlank(message = "Username field can not be empty")
    private String username;

    @NotBlank(message = "Email filed can not be empty")
    @Email(message = "Email field has incorrect format")
    private String email;

    @NotBlank
    @Size(min = 7, max = 20, message = "Password must be longer than 7 characters, "
        + "but no more than 20 characters")
    private String password;
}
