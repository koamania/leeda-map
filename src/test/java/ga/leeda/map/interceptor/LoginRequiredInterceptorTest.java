package ga.leeda.map.interceptor;

import ga.leeda.map.common.AnnotationUtils;
import ga.leeda.map.common.SessionManager;
import ga.leeda.map.interceptor.annotations.LoginRequired;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnnotationUtils.class, SessionManager.class})
public class LoginRequiredInterceptorTest {

    private LoginRequiredInterceptor interceptor;

    @Before
    public void setUp() {
        this.interceptor = new LoginRequiredInterceptor();
    }

    @Test
    public void no_annotated_login_required() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HandlerMethod handlerMethod = mock(HandlerMethod.class);

        mockStatic(AnnotationUtils.class);
        when(AnnotationUtils.isAnnotatedWith(handlerMethod, LoginRequired.class)).thenReturn(false);
//        when(AnnotationUtils.getAnnotation(handlerMethod, LoginRequired.class)).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, handlerMethod);
        assertTrue("LoginRequired annotation이 붙지 않았는데 false", result);
//        SessionManager.get(SessionManager.SessionKey.LOGIN_INFO)
    }

    @Test
    public void login_user_test() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HandlerMethod handlerMethod = mock(HandlerMethod.class);

        mockStatic(AnnotationUtils.class);
        mockStatic(SessionManager.class);
        // 로그인 상황 가정
        when(SessionManager.get(SessionManager.SessionKey.LOGIN_INFO)).thenReturn(new Object());
        when(AnnotationUtils.isAnnotatedWith(handlerMethod, LoginRequired.class)).thenReturn(true);

        boolean result = interceptor.preHandle(request, response, handlerMethod);
        assertTrue("LoginRequired annotation이 붙지 않았는데 false", result);
    }

    @Test
    public void no_login_user_test() throws Exception {
        HandlerMethod handlerMethod = mock(HandlerMethod.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        doNothing().when(response, "sendRedirect", "/");

        mockStatic(AnnotationUtils.class);
        mockStatic(SessionManager.class);
        // 로그인 상황 가정
        when(SessionManager.get(SessionManager.SessionKey.LOGIN_INFO)).thenReturn(null);
        when(AnnotationUtils.isAnnotatedWith(handlerMethod, LoginRequired.class)).thenReturn(true);
        when(AnnotationUtils.getAnnotation(handlerMethod, LoginRequired.class)).thenReturn(stubLoginRequired(LoginRequired.ErrorHandler.REDIRECT));

        boolean result = interceptor.preHandle(request, response, handlerMethod);
        assertFalse("로그인 되어있는데 true로 반환됨.", result);
    }

    @Test
    public void no_login_user_with_json_test() throws Exception {
        HandlerMethod handlerMethod = mock(HandlerMethod.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter write = mock(PrintWriter.class);
        doNothing().when(write, "write", "");

        doNothing().when(response, "setContentType", MimeTypeUtils.APPLICATION_JSON_VALUE);
        doNothing().when(response, "setStatus", HttpStatus.UNAUTHORIZED.value());
        doNothing().when(response, "setCharacterEncoding", StandardCharsets.UTF_8.name());
        when(response, "getWriter").thenReturn(write);

        mockStatic(AnnotationUtils.class);
        mockStatic(SessionManager.class);
        // 로그인 상황 가정
        when(SessionManager.get(SessionManager.SessionKey.LOGIN_INFO)).thenReturn(null);
        when(AnnotationUtils.isAnnotatedWith(handlerMethod, LoginRequired.class)).thenReturn(true);
        when(AnnotationUtils.getAnnotation(handlerMethod, LoginRequired.class)).thenReturn(stubLoginRequired(LoginRequired.ErrorHandler.RESPONSE_JSON));

        boolean result = interceptor.preHandle(request, response, handlerMethod);
        assertFalse("로그인 되어있는데 true로 반환됨.", result);
    }

    private Optional<LoginRequired> stubLoginRequired(LoginRequired.ErrorHandler errorHandler) {
        return Optional.of(new LoginRequired() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public ErrorHandler handler() {
                return errorHandler;
            }
        });
    }
}