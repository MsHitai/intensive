package ru.t1.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * Аспект для асинхронного отслеживания времени выполнения методов
 */
@Aspect
@Component
@Slf4j
public class TrackTimeAsyncAspect {

    /**
     * Метод для асинхронного отслеживания времени выполнения методов
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return {@link Object}
     * @throws Throwable {@link Throwable} исключение
     */
    @Around("@annotation(TrackTimeAsync)")
    public Object trackTimeAsync(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        Executors.newFixedThreadPool(2).execute(() -> {
            CompletableFuture.runAsync(() -> {
                log.info("Started async calculations at {}", startTime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Ran into error, reason: {}", e.getMessage(), e);
                }
            });
        });
        long endTime = System.currentTimeMillis();
        log.info("Finished async calculations at {}", endTime);
        log.info("The time for async calculations is {} milliseconds", endTime - startTime);
        return proceed;
    }
}
