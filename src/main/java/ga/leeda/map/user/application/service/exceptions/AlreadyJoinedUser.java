package ga.leeda.map.user.application.service.exceptions;

public class AlreadyJoinedUser extends RuntimeException {
    public AlreadyJoinedUser(final String message) {
        super(message);
    }
}
