package com.github.ormfux.simple.orm.generators;

/**
 * A generator that does nothing but throwing an Exception.
 */
public final class NoValueGenerator implements ValueGenerator<Void> {
    
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public Void generate(final Object previousValue) {
        throw new UnsupportedOperationException();
    }
    
}
