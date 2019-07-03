package ga.leeda.map.common;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Session 관리를 위한 클래스
 * security를 이용해서 session 관리등을 하면 좋겠지만...
 */
public class SessionManager {

    /**
     * session에 값을 넣는다.
     *
     * @param name {@link SessionKey} session에 저장될 key
     * @param attr session에 저장될 value
     * @param <T>  session에 저장될 데이터 형식
     */
    public static <T> void set(SessionKey name, T attr) {
        HttpSession session = getSession();
        session.setAttribute(name.key, attr);
    }

    /**
     * session으로부터 값을 가져온다
     *
     * @param name {@link SessionKey} session에 저장된 key
     * @param <T>  session에 저장된 데이터의 형식. 형식이 맞지 않을 경우 ClassCastException 발생
     * @return session에서 가져온 값
     */
    @Nullable
    public static <T> T get(SessionKey name) {
        HttpSession session = getSession();
        //noinspection unchecked
        return (T) session.getAttribute(name.key);
    }

    private static HttpSession getSession() {
        ServletRequestAttributes requestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttribute.getRequest().getSession(true);
    }

    /**
     * session에 저장될 키들을 열거
     */
    @Getter
    public enum SessionKey {
        /**
         * 로그인 정보
         */
        LOGIN_INFO("login_info");

        /**
         * raw string
         */
        private final String key;

        SessionKey(final String key) {
            this.key = key;
        }
    }
}
