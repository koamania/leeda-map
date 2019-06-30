package ga.leeda.map.interceptor;

import ga.leeda.map.common.SessionManager;
import ga.leeda.map.interceptor.annotations.LoginRequired;
import ga.leeda.map.interceptor.exception.UnauthorizedRequestExcecption;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

@Component
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!isAnnotatedWith((HandlerMethod) handler, LoginRequired.class)) {
            return true;
        }


        if (SessionManager.get(SessionManager.SessionKey.LOGIN_INFO) == null) {
            throw new UnauthorizedRequestExcecption("login required");
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private static boolean isAnnotatedWith(HandlerMethod handler, Class annotation) {
        Class handlerClass = handler.getMethod().getDeclaringClass();
        Annotation check1 = handlerClass.getDeclaredAnnotation(annotation);
        boolean isAnnotatedToClass = check1 != null;

        Annotation check2 = handler.getMethodAnnotation(annotation);
        boolean isAnnotatedToMethod = check2 != null;

        return isAnnotatedToClass || isAnnotatedToMethod;
    }
}
