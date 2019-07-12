package com.github.ormfux.simple.orm.generators;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.github.ormfux.common.utils.DateUtils;
import com.github.ormfux.simple.orm.generators.DateNowGenerator;

public class DateNowGeneratorTest {
    
    @Test
    public void testGenerate() {
        Date generatedValue = new DateNowGenerator().generate(null);
        assertNotNull(generatedValue);
        assertTrue(DateUtils.now().getTime() - generatedValue.getTime() < 2000);
        
        generatedValue = new DateNowGenerator().generate("param");
        assertNotNull(generatedValue);
        assertTrue(DateUtils.now().getTime() - generatedValue.getTime() < 2000);
        
        generatedValue = new DateNowGenerator().generate(DateUtils.getDate(2000, 1, 1));
        assertNotNull(generatedValue);
        assertTrue(DateUtils.now().getTime() - generatedValue.getTime() < 2000);
        
    }
    
}
