package cz.muni.fi.pa165.currency;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;

@Named
@Aspect
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        System.err.println("Calling method: "
                + joinPoint.getSignature());

        Object result = joinPoint.proceed();

        System.err.println("Method finished: "
                + joinPoint.getSignature());

        return result;
    }
}
