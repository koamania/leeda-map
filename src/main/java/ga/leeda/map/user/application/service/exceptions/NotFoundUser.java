package ga.leeda.map.user.application.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundUser extends Exception {
    public static ResponseStatusException with(HttpStatus status, String reason) {
        return new ResponseStatusException(status, reason);
    }

    public static RuntimeException with(String message) {
        return new RuntimeException(message);
    }
}
