package ga.leeda.map.user.application.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundUser extends ResponseStatusException {
    public NotFoundUser(final HttpStatus status, final String reason) {
        super(status, reason);
    }
}
