package ga.leeda.map.interceptor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    ErrorHandler handler() default ErrorHandler.REDIRECT;

    enum ErrorHandler {
        REDIRECT,
        RESPONSE_JSON
    }
}