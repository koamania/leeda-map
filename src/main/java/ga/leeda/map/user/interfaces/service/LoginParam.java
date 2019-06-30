package ga.leeda.map.user.interfaces.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class LoginParam {
    @Email
    @NotNull
    private final String email;
    @NotNull
    private final String password;

}
