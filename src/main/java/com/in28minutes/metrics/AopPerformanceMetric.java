package com.in28minutes.metrics;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

/**
 * This class uses AOP to measure the performance of the specified functions.
 *
 * @author Christos Patsouras
 */
@Aspect
@Configuration
public class AopPerformanceMetric {
    /**
     * The performance logger.
     */
    private final Logger log = Logger.getLogger("performanceLog");

    /**
     * Runs around every method in the specified packages, writes
     *
     * @param joinPoint
     *          The intercepted method.
     * @return
     *          The returned value of the intercepted method.
     * @throws Throwable
     *          joinPoint.proceed may throw Throwable
     */
    @Around("execution(* com.in28minutes.controllers.*.*(..)) || " +
            "execution(* com.in28minutes.service.*.*(..)) || " +
            "execution(* com.in28minutes.repositories.*.*(..))")
    public Object performanceLogs(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object ret = joinPoint.proceed();
        stopWatch.stop();
        log.info(joinPoint.getSignature() + " - " + stopWatch.getTotalTimeMillis() + " ms");
        return ret;
    }
}