package com.github.ormfux.simple.orm.query.testabstractquery;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Map;

import com.github.ormfux.simple.orm.query.AbstractQuery;
import com.github.ormfux.simple.orm.query.connection.AbstractDbConnectionProvider;
import com.github.ormfux.simple.orm.query.connection.DbConnectionProvider;

public abstract class AbstractAbstractQueryTest {
    
    @SuppressWarnings("unchecked")
    protected Map<String, Object> getParams(AbstractQuery query) {
        try {
            Field queryParamsField = AbstractQuery.class.getDeclaredField("queryParams");
            queryParamsField.setAccessible(true);
            
            Map<String, Object> queryParams = (Map<String, Object>) queryParamsField.get(query);
            assertNotNull(queryParams);
            
            return queryParams;
            
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Cannot access field.", e);
        }
        
    }
    
    protected static class MockQuery extends AbstractQuery {
        
        public MockQuery(DbConnectionProvider connectionProvider) {
            super(connectionProvider, null);
        }
    }
    
    protected static class MockConnectionProvider extends AbstractDbConnectionProvider {

        public MockConnectionProvider() {
            super(AbstractAbstractQueryTest.class.getName(), null);
        }

        @Override
        public boolean isCanBackupDatabase() {
            return false;
        }

        @Override
        public boolean ping() {
            return false;
        }

        @Override
        public Connection getConnection() {
            return null;
        }

        @Override
        public void closeAllConnections() {
        }
        
    }
    
}
