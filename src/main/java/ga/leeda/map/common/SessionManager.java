package ga.leeda.map.common;

import lombok.Getter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionManager {

    public static <T> void set(SessionKey name, T attr) {
        HttpSession session = getSession();
        session.setAttribute(name.key, attr);
    }

    public static <T> T get(SessionKey name) {
        HttpSession session = getSession();
        //noinspection unchecked
        return (T) session.getAttribute(name.key);
    }

    private static HttpSession getSession() {
        ServletRequestAttributes requestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttribute.getRequest().getSession(true);
    }

    @Getter
    public enum SessionKey {
        LOGIN_INFO("login_info");

        private final String key;

        SessionKey(final String key) {
            this.key = key;
        }
    }
}
