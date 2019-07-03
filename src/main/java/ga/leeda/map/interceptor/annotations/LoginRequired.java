package ga.leeda.map.interceptor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인 필수 여부를 인터셉터에서 검증하기 위한 클래스
 * {@link ga.leeda.map.interceptor.LoginRequiredInterceptor}
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    ErrorHandler handler() default ErrorHandler.REDIRECT;

    /**
     * 비로그인 사용자 처리를 위한 클래스
     * interceptor에서 content타입에 따라서 response를 다르게해야하지만
     * wrapper를 만들기에는 시간이 없어서 ㅠㅠ
     */
    enum ErrorHandler {
        REDIRECT,
        RESPONSE_JSON
    }
}