package de.bs.cli.jpar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.bs.cli.jpar.config.Consts;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Arguments {
	String name() default Consts.NAME;
	String delimiter() default Consts.EMPTY;
	String[] values() default {};
}
