package com.ppdai.das.client.annotation;

import com.ppdai.das.client.transaction.DasTransactionalEnabler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DasTransactionalEnabler.class)
public @interface EnableDasTransaction {}
