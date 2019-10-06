package com.example.aop.interceptor;

import com.example.aop.annotation.ForceUpdate;
import com.example.aop.annotation.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
@Aspect
@Slf4j
public class MyInterceptor {

    private final ConfigurableBeanFactory factory;

    @Pointcut("execution(* *(..)) && ( within(com.example.aop.api..*) ) )")
    public void anyClassInSubpackagesInApi() {
    }

    @Around("anyClassInSubpackagesInApi() && @annotation(ForceUpdate) && args(.., authentication)")
    public Object checkVersion(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = proceedingJoinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes());
        ForceUpdate forceUpdate = method.getAnnotation(ForceUpdate.class);

        final Map<Integer, String> platformVersionMap = Stream.of(forceUpdate.platforms()).collect(Collectors.toMap(Platform::platformType, Platform::version));

//        final String checkingVersionOfPlatform = platformVersionMap.get(principal.getPlatformType());

//        if ( Ob.isEmpty(checkingVersionOfPlatform) || VersionUtil.isVersionBigger(principal.getAppVersion(), resolveEmbeddedValue(checkingVersionOfPlatform))  ) {
//            return proceedingJoinPoint.proceed();
//        }

        throw (RuntimeException) forceUpdate.returnType().newInstance();
    }

    private String resolveEmbeddedValue(String checkingVersionOfPlatform) {
        return factory.resolveEmbeddedValue(checkingVersionOfPlatform);
    }

}
