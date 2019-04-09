package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Component
@Aspect
public class LogAop {

    @Autowired
    private ISysLogService iSysLogService;
    @Autowired
    private HttpServletRequest request;

    private Date visitTime;
    private Class clazz;
    private Method method;

    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws Exception {
        visitTime = new Date();//开始的时间
        clazz = jp.getTarget().getClass();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        if (args == null &&args.length == 0) {
            method = clazz.getMethod(methodName);
        } else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();

            }
            method = clazz.getMethod(methodName, classArgs);
        }

    }


    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) {

        Long time = System.currentTimeMillis()-visitTime.getTime();
        String path = null;


        //获取注解中的路径，拼接
        if (clazz != null && method != null && clazz != SysLogController.class) {
            RequestMapping class_annotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (class_annotation != null) {
                String[] class_value = class_annotation.value();
                RequestMapping method_annotation = (RequestMapping) method.getAnnotation(RequestMapping.class);
                if (method_annotation != null) {
                    String[] method_value = method_annotation.value();
                    path = class_value[0] + method_value[0];
                    //获取访问者ip
                    String IPAddr = request.getRemoteAddr();
                    //获取用户名
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    User user = (User) securityContext.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    SysLog sysLog = new SysLog();
                    sysLog.setVisitTime(visitTime);
                    sysLog.setUsername(username);
                    sysLog.setIp(IPAddr);
                    sysLog.setUrl(path);
                    sysLog.setExecutionTime(time);
                    sysLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());

                    iSysLogService.savaLog(sysLog);
                }
            }
        }


    }
}
