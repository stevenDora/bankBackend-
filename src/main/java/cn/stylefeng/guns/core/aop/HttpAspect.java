package cn.stylefeng.guns.core.aop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * peter
 * aop切面，对每一个在controller请求之前做记录
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);


    @Pointcut("execution(public * cn.stylefeng.guns.modular.system.service.*.*(..))||" +
            "execution(public * cn.stylefeng.guns.modular.system.controller.*.*(..))")//切面所切的位置
    public void log(){
    }

    @Before("log()")//请求之前
    public void before(JoinPoint joinPoint){
        LOGGER.info("请求信息 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        LOGGER.info("url={}",request.getRequestURL());
        //method
        LOGGER.info("method={}",request.getMethod());
        //ip
        LOGGER.info("id={}",request.getRemoteAddr());
        //class_method
        LOGGER.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        LOGGER.info("args={}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "log()",returning = "object")//打印输出结果
    public void doAfterReturning(Object object){
        LOGGER.info("response={}",new Gson().toJson(object));
    }
}
