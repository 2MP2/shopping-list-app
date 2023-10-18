package pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation;

import jakarta.annotation.security.RolesAllowed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@RolesAllowed({"USER", "ADMIN"})
public @interface ForLogin {
}
