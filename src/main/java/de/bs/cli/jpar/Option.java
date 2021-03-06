package de.bs.cli.jpar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.bs.cli.jpar.config.Consts;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Option {
	String name();
	String description();
	boolean required() default Consts.REQUIRED;
	Class<?> sourceType() default Void.class;
}
