package ga.leeda.map.common;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * HandlerMethod 객체로부터 어노테이션 정보를 가져오는 유틸 클래스
 */
public class AnnotationUtils {
    private AnnotationUtils() {
        // no - op
    }

    /**
     * annotation이 선언되어 있는 메소드 혹은 클래스인지를 판별
     *
     * @param handler         HandlerMethod
     * @param annotationClass annotation 클래스
     * @return annotationClass가 포함된 메소드인지 확인
     */
    @SuppressWarnings("unchecked")
    public static boolean isAnnotatedWith(HandlerMethod handler, Class annotationClass) {
        Class handlerClass = handler.getMethod().getDeclaringClass();
        Annotation check1 = handlerClass.getDeclaredAnnotation(annotationClass);
        boolean isAnnotatedToClass = check1 != null;

        Annotation check2 = handler.getMethodAnnotation(annotationClass);
        boolean isAnnotatedToMethod = check2 != null;

        return isAnnotatedToClass || isAnnotatedToMethod;
    }

    /**
     * HandlerMethod에서 특정 annotation을 가져오는 메소드
     * 우선순위 : method annotation -> class annotation\
     *
     * @param handler         대상 HandlerMethod
     * @param annotationClass 가져오려는 Annotation
     * @param <T>             annotation type
     * @return Optional 형태로 annotation을 반환
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> Optional<T> getAnnotation(HandlerMethod handler, Class<T> annotationClass) {
        Class handlerClass = handler.getMethod().getDeclaringClass();
        Annotation classDeclaredAnnotation = handlerClass.getDeclaredAnnotation(annotationClass);
        Annotation methodDeclaredAnnotation = handler.getMethodAnnotation(annotationClass);

        if (methodDeclaredAnnotation != null) {
            return Optional.of((T) methodDeclaredAnnotation);
        }

        if (classDeclaredAnnotation != null) {
            return Optional.of((T) classDeclaredAnnotation);
        }

        return Optional.empty();
    }
}
