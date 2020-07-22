package com.hzcominfo.aop.aspect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.hzcominfo.aop.LoginActivity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // 定义切面类
public class LoginCheckAspect {

    private final static String TAG = LoginCheckAspect.class.getSimpleName();

    /**
     * 找到应用中的切入点（使用注解的方法）
     * @param  execution 以注解的方法作为切点，触发Aspect类。注意：里面的值必须是自定义依赖的正确路径！
     * @param * *(..)) 可以处理ClickBehavior这个类所有的方法
     */
    @Pointcut("execution(@com.hzcominfo.aop.annotation.LoginCheck * *(..))")
    public void methodPointCut() {}

    /**
     * 对切入点进行处理
     * @param joinPoint 切入点
     * @return 返回对象
     * @throws Throwable
     */
    @Around("methodPointCut()")
    public Object jointPotin(ProceedingJoinPoint joinPoint) throws Throwable {
        Context context = (Context) joinPoint.getThis();
        boolean isLogin = true; //模拟登录状态，一般从SharedPreferences中读取
        if (isLogin) {
            Log.e(TAG, "检测到已登录！");
            return joinPoint.proceed(); //切面方法执行
        } else { //我的专区、我的优惠券、我的积分这些页面必须在登录之后跳转
            Log.e(TAG, "检测到未登录！");
            Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            return null; // 不再执行方法（切入点）
        }
    }
}
