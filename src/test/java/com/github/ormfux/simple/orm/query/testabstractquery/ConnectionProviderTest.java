package com.github.ormfux.simple.orm.query.testabstractquery;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;

import com.github.ormfux.common.utils.object.Objects;
import com.github.ormfux.simple.orm.query.AbstractQuery;

public class ConnectionProviderTest extends AbstractAbstractQueryTest {
    
    @Test(expected = NullPointerException.class)
    public void testNoConnectionProvider() {
        new MockQuery(null);
    }
    
    @Test
    public void testWithConnectionProvider() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final MockConnectionProvider connectionProvider = new MockConnectionProvider();
        
        MockQuery query = new MockQuery(connectionProvider);
        
        Field connectionProviderField = AbstractQuery.class.getDeclaredField("dbConnectionProvider");
        connectionProviderField.setAccessible(true);
        
        assertTrue(Objects.isSame(connectionProvider, connectionProviderField.get(query)));
    }
    
}
