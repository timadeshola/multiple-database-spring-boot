package com.example.multipledatabasespringboot.core.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 9:03 AM
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static String constructLogMsg(JoinPoint jp) {
        var args = Arrays.stream(jp.getArgs()).map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        var method = ((MethodSignature) jp.getSignature()).getMethod();
        return "@" + method.getName() + ":" + args;
    }

    @SneakyThrows
    @Around("logAroundPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        var classMethod = joinPoint.getSignature().getDeclaringTypeName();
        var methodName = joinPoint.getSignature().getName();
        var argument = Arrays.toString(joinPoint.getArgs());
        var className = joinPoint.getTarget().getClass().toString();
        Object result = joinPoint.proceed();
        if (log.isDebugEnabled()) {
            log.debug("Enter: className {} method {}.{}() with argument[s] = {}",
                    className,
                    classMethod,
                    methodName,
                    argument);
        } else {
            log.info("Enter: className {} method {}.{}() with argument[s] = {}",
                    className,
                    classMethod,
                    methodName,
                    argument);
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", classMethod, methodName, result.toString());
            } else {
                log.info("Exit: {}.{}() with result = {}", classMethod, methodName, result.toString());
            }
            return result;
        } catch (RuntimeException e) {
            log.error("Runtime exception: ClassName {}, {} in {}.{}()", className, argument, classMethod, methodName);
            throw e;
        }
    }

    @Pointcut(value = "execution(* com.example.unittesting.*.*.*(..) ))")
    public void logAroundPointcut() {
        // default implementation ignored
    }

    @Around("logAroundPointcut()")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        var className = methodSignature.getDeclaringType().getSimpleName();
        var methodName = methodSignature.getName();

        final var stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        log.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }

    @Around("logAroundPointcut()")
    @SneakyThrows
    public Object logAroundExec(ProceedingJoinPoint pjp) {
        log.info("before {}", constructLogMsg(pjp));
        var proceed = pjp.proceed();
        log.info("after {} with result: {}", constructLogMsg(pjp), proceed.toString());
        return proceed;
    }

    @AfterThrowing(pointcut = "execution(* com.example.unittesting.*.*.*(..) ) )", throwing = "e")
    public void logAfterException(JoinPoint jp, Exception e) {
        log.error("Exception during: {} with ex: {}", constructLogMsg(jp), e.toString());
    }
}