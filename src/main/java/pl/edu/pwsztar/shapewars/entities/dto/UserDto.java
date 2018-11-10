package pl.edu.pwsztar.shapewars.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.shapewars.annotations.PasswordMatches;
import pl.edu.pwsztar.shapewars.annotations.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private String matchingPassword;
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

}
