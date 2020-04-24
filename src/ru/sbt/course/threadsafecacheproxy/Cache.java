package ru.sbt.course.threadsafecacheproxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    static int FILE = 0;
    static int IN_MEMORY =1;
    int cachType() default IN_MEMORY;
    String fileName() default "data.cch";
    boolean zip() default false;
    Class[] identityBy() default {};
    int listLength() default 0;
}
