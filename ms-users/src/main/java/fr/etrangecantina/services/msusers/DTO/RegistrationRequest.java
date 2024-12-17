package fr.etrangecantina.services.msusers.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @Email(message = "Email is not formatted properly")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    private String username;
    @NotEmpty(message = "password is mandatory")
    @NotBlank(message = "password is mandatory")
    @Size(min = 8, message = "Password is too short, 8 characters long minimum")
    private String password;
}
