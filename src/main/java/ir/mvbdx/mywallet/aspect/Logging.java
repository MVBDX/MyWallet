package ir.mvbdx.mywallet.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class Logging {
    private final Logger LOGGER = LogManager.getLogger(getClass().getName());

    @Pointcut("execution(* ir.mvbdx.mywallet.service.impl.*.*(..))")
    private void forServicePackage() { }

    @Pointcut("within(ir.mvbdx.mywallet.controller.*)")
    private void forControllerPackage() { }

    @Pointcut("execution(* get*(..))")
    private void forGetterMethods() { }

    @Pointcut("execution(* set*(..))")
    private void forSetterMethods() { }

    @Before("forServicePackage() && !forSetterMethods() && !forGetterMethods() || @annotation(Loggable)")
    public void beforeAllServiceMethods(JoinPoint joinPoint) {
        LOGGER.info("@Before execute of :: " + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "." + joinPoint.getSignature().getName());
    }

    @After("forServicePackage()")
    public void afterAllMethods(JoinPoint joinPoint) {
        LOGGER.info("@After execute of :: " + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "." + joinPoint.getSignature().getName());
    }

    @Around("forServicePackage() || forControllerPackage()")
    public Object aroundAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        LOGGER.info("@Around :: Execution time of " + className + "." + methodName + " :: "
                + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }
}