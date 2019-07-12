package com.github.ormfux.simple.orm.generators;

import org.junit.Test;

import com.github.ormfux.simple.orm.generators.NoValueGenerator;

public class NoValueGeneratorTest {
    
    @Test(expected = UnsupportedOperationException.class)
    public void testGenerate() {
        new NoValueGenerator().generate(null);
    }
}
