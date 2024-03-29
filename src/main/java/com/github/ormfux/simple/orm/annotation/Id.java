package com.github.ormfux.simple.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.ormfux.simple.orm.generators.IdGenerator;

/**
 * Annotation for fields that represent the id of an entity.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
    /**
     * The generator, which creates the id of the entity upon persisting it for 
     * the first time.
     */
    public Class<? extends IdGenerator> value();
    
}
