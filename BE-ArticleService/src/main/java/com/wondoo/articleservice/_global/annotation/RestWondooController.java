package com.wondoo.articleservice._global.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
@RequestMapping("/article-service")
public @interface RestWondooController {
    @AliasFor(
            annotation = Controller.class
    )
    String value() default "";
}