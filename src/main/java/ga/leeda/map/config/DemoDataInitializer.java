package ga.leeda.map.config;

import ga.leeda.map.user.application.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer {

    private final UserService userService;

    public DemoDataInitializer(final UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void insertDemoData(ApplicationReadyEvent event) {
        this.userService.insertNewUser("koamania2@gmail.com", "1234");
    }
}
