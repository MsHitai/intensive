package ru.t1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для синхронного отслеживания времени выполнения методов
 */
@Aspect
@Component
@Slf4j
public class TrackTimeAspect {

    /**
     * Метод для синхронного отслеживания времени выполнения методов
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return {@link Object}
     * @throws Throwable {@link Throwable} исключение
     */
    @Around("@annotation(TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("Starting tracking time at {}", startTime);
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Ending tracking time at {}", endTime);
        return proceed;
    }
}
