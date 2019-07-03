package ga.leeda.map.user.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 로그인에 필요한 파라미터
 */
@Getter
@AllArgsConstructor
public class LoginParam {
    @Email
    @NotNull
    private final String email;
    @NotNull
    private final String password;

}
