package ga.leeda.map.config;

import ga.leeda.map.user.application.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 어플리케이션 실행 시 demo data를 지정하기 위한 설정
 * import.sql로 설정하게되면 fail-fast이기떄문에
 * 어플리케이션 실행에 완전히 필수적인 데이터는 아니라서 여기다 설정
 */
@Component
public class DemoDataInitializer {

    private final UserService userService;

    public DemoDataInitializer(final UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void insertDemoData(ApplicationReadyEvent event) {
        this.userService.insertNewUser("iwantkakao@daum.net", "123123!");
    }
}
