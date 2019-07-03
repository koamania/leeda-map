package ga.leeda.map.config;

import ga.leeda.map.interceptor.LoginRequiredInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터 전역 설정
 */
@Configuration
public class InterceptorRegister implements WebMvcConfigurer {

    private final LoginRequiredInterceptor loginRequiredInterceptor;

    public InterceptorRegister(final LoginRequiredInterceptor loginRequiredInterceptor) {
        this.loginRequiredInterceptor = loginRequiredInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor);
    }
}
