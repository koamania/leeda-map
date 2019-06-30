package ga.leeda.map.interceptor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Login required")
public class UnauthorizedRequestExcecption extends RuntimeException {

    public UnauthorizedRequestExcecption(final String message) {
        super(message);
    }
}
