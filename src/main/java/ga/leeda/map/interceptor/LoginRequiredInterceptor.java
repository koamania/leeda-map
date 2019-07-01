package ga.leeda.map.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ga.leeda.map.common.AnnotationUtils;
import ga.leeda.map.common.SessionManager;
import ga.leeda.map.interceptor.annotations.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!AnnotationUtils.isAnnotatedWith((HandlerMethod) handler, LoginRequired.class)) {
            return true;
        }

        if (SessionManager.get(SessionManager.SessionKey.LOGIN_INFO) == null) {
            Optional<LoginRequired> annotation = AnnotationUtils.getAnnotation((HandlerMethod) handler, LoginRequired.class);
            if (annotation.isPresent() && annotation.get().handler() == LoginRequired.ErrorHandler.RESPONSE_JSON) {
                return responseJSONError(response);
            } else {
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }

    private boolean responseJSONError(final HttpServletResponse response) throws IOException {
        ObjectNode responseJson = MAPPER.createObjectNode();
        responseJson.put("message", "로그인이 필요한 서비스입니다.");

        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(MAPPER.writeValueAsString(responseJson));
        return false;
    }
}
