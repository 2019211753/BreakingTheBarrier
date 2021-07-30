package com.lrm.annotation;

import com.lrm.validation.AccountInfoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

    /**
     * 自定义校验注解
     * 提示:
     * 1、message、contains、payload是必须要写的
     * 2、还需要什么方法可根据自己的实际业务需求，自行添加定义即可
     * 注:当没有指定默认值时，那么在使用此注解时，就必须输入对应的属性值
     *
     * @author 山水夜止
     */
    @Target({FIELD, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    //指定此注解的实现，即:验证器
    @Constraint(validatedBy = {AccountInfoValidator.class})
    public @interface AccountInfoFormat {

        /**
         * 当验证不通过时的提示信息
         */
        String message() default "请输入正确密码格式————长度为7至12且不能包含汉字、至少含有一个英文字母";

        /**
         * 根据实际需求
         */
        String need() default "true";

        // 约束注解在验证时所属的组别
        Class<?>[] groups() default {};

        // 负载
        Class<? extends Payload>[] payload() default {};
    }

