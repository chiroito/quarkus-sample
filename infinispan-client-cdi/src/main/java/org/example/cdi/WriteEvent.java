package org.example.cdi;

import jakarta.inject.Qualifier;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@Qualifier
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface WriteEvent {
}
