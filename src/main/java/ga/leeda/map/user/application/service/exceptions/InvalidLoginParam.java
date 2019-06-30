package ga.leeda.map.user.application.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "invalid login info")
public class InvalidLoginParam extends RuntimeException {

    public InvalidLoginParam() {
        super();
    }
}
