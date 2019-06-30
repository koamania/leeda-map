package ga.leeda.map.user.application.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "password encryption failed")
public class PasswordEncryptionError extends RuntimeException {

}
