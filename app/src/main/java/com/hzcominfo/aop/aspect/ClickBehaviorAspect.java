package com.hzcominfo.aop.aspect;

import android.util.Log;
import com.hzcominfo.aop.annotation.ClickBehavior;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect // 定义切面类
public class ClickBehaviorAspect {

    private final static String TAG = ClickBehaviorAspect.class.getSimpleName();

    /**
     * 找到应用中的切入点（使用注解的方法）
     * @param  execution 以注解的方法作为切点，触发Aspect类。注意：里面的值必须是自定义依赖的正确路径！
     * @param * *(..)) 可以处理ClickBehavior这个类所有的方法
     */
    @Pointcut("execution(@com.hzcominfo.aop.annotation.ClickBehavior * *(..))")
    public void methodPointCut() {}

    /**
     * 对切入点进行处理
     * @param joinPoint 切入点
     * @return 返回对象
     * @throws Throwable
     */
    @Around("methodPointCut()")
    public Object jointPotin(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取签名方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取方法所属的类名
        String className = methodSignature.getDeclaringType().getSimpleName();

        // 获取方法名
        String methodName = methodSignature.getName();

        // 获取方法的注解值(需要统计的用户行为)
        String funName = methodSignature.getMethod().getAnnotation(ClickBehavior.class).value();

        // 统计方法的执行时间、统计用户点击某功能行为。（存储到本地，每过x天上传到服务器）
        long begin = System.currentTimeMillis();
        Log.e(TAG, "ClickBehavior Method Start >>> ");
        Object result = joinPoint.proceed(); // MainActivity中切面的方法
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG, "ClickBehavior Method End >>> ");
        Log.e(TAG, String.format("统计：%s功能，在%s类的%s方法，用时%d ms",
                funName, className, methodName, duration));

        return result;
    }
}
