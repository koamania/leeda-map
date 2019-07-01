package ga.leeda.map.common;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class AnnotationUtils {
    @SuppressWarnings("unchecked")
    public static boolean isAnnotatedWith(HandlerMethod handler, Class annotationClass) {
        Class handlerClass = handler.getMethod().getDeclaringClass();
        Annotation check1 = handlerClass.getDeclaredAnnotation(annotationClass);
        boolean isAnnotatedToClass = check1 != null;

        Annotation check2 = handler.getMethodAnnotation(annotationClass);
        boolean isAnnotatedToMethod = check2 != null;

        return isAnnotatedToClass || isAnnotatedToMethod;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> Optional<T> getAnnotation(HandlerMethod handler, Class<T> annotationClass) {
        Class handlerClass = handler.getMethod().getDeclaringClass();
        Annotation classDeclaredAnnotation = handlerClass.getDeclaredAnnotation(annotationClass);
        Annotation methodDeclaredAnnotation = handler.getMethodAnnotation(annotationClass);

        if (classDeclaredAnnotation != null) {
            return Optional.of((T) classDeclaredAnnotation);
        }

        if (methodDeclaredAnnotation != null) {
            return Optional.of((T) methodDeclaredAnnotation);
        }

        return Optional.empty();
    }
}
