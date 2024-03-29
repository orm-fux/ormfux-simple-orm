package com.github.ormfux.simple.orm.generators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.ormfux.simple.orm.generators.LongIncrementGenerator;

public class LongIncrementGeneratorTest {
    
    @Test
    public void testGenerate() {
        assertEquals(Long.valueOf(0L),new LongIncrementGenerator().generate(null));
        assertEquals(Long.valueOf(1L),new LongIncrementGenerator().generate(0L));
        assertEquals(Long.valueOf(2L),new LongIncrementGenerator().generate(Long.valueOf(1L)));
    }
    
    @Test(expected = ClassCastException.class)
    public void testGenerateWrongInput() {
        new LongIncrementGenerator().generate("wrong type");
    }
    
}
