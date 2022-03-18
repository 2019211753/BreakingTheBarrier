package com.lrm.aspect;

import com.lrm.dao.UserRepository;
import com.lrm.exception.NotFoundException;
import com.lrm.po.User;
import com.lrm.util.TokenInfo;
import com.lrm.util.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class WebAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;
    @Pointcut("execution(* com.lrm.web.FileController.*(..))")
    public void fileCut() {
    }

    @Pointcut("execution(* com.lrm.web.InfoEntryController.*(..))")
    public void infoCut() {
    }

    @Pointcut("execution(* com.lrm.web.TeamController.*(..))")
    public void teamCut() {
    }

    @Pointcut("execution(* com.lrm.web.WebsocketController.*(..))")
    public void socketCut() {
    }

    @Pointcut("infoCut() || fileCut() || teamCut()")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("-------do before web-------");
        Signature signature = joinPoint.getSignature();
        String packName = signature.getDeclaringTypeName();
        String funcName = signature.getName();
        //为了获取request来得到userId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Long userId = TokenInfo.getCustomUserId(attributes.getRequest());
        //没有token的访问,例如登录,直接执行方法
        if (userId == -1) {
            logger.info("即将执行方法" + packName + "." + funcName);
            return;
        }
        Optional<User> localUser = userRepository.findById(userId);
        if (!localUser.isPresent()) throw new NotFoundException("需要绑定的用户不存在!");
        //提前绑定user到线程中
        UserHolder.setLocalUser(localUser.get());
        logger.info("user成功绑定到线程: " + Thread.currentThread());
        logger.info("user信息: " + UserHolder.getLocalUser().toString());
        logger.info("即将执行方法" + packName + "." + funcName);
    }

    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("--------do after web--------");
        if (UserHolder.getLocalUser() != null) {
            UserHolder.clear();
            logger.info("与线程{}绑定的user已清除", Thread.currentThread());
        }
        Signature signature = joinPoint.getSignature();
        String packName = signature.getDeclaringTypeName();
        String funcName = signature.getName();
        logger.info("{}.{}方法已执行", packName, funcName);
    }

//    @AfterReturning(value = "pointCut()", returning = "result")
//    @AfterThrowing(value = , throw = )
}
