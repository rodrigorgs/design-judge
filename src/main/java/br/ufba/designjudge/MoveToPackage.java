package br.ufba.designjudge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method that should be empty in the assignment.
 * Optionally, can provide the code that replace
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MoveToPackage {
	public String name() default "";
}