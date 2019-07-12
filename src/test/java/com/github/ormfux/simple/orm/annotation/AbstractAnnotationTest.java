package com.github.ormfux.simple.orm.annotation;

import java.lang.reflect.Field;
import java.sql.Connection;

import org.junit.After;
import org.junit.Before;

import com.github.ormfux.simple.orm.query.QueryManager;
import com.github.ormfux.simple.orm.query.connection.DbConnectionProvider;
import com.github.ormfux.simple.orm.query.connection.H2DbConnectionProvider;

public abstract class AbstractAnnotationTest {
    
    protected QueryManager queryManager;
    
    private String dbName;
    
    public AbstractAnnotationTest(String dbName) {
        this.dbName = dbName;
    }
    
    @Before
    public void beforeTest() {
        queryManager = new QueryManager();
        queryManager.setDatabase(H2DbConnectionProvider.class, 
                                 "jdbc:h2:mem:" + dbName, 
                                 "DB_CLOSE_DELAY=-1", 
                                 "MODE=MYSQL", 
                                 "DATABASE_TO_UPPER=false", 
                                 "AUTOCOMMIT=false");
        queryManager.createQuery("DROP ALL OBJECTS").executeUpdate();
    }
    
    @After
    public void afterTest() {
        DbConnectionProvider connectionProvider = getConnectionProvider();
        connectionProvider.closeAllConnections();
    }

    protected Connection getDbConnection() {
        return getConnectionProvider().getConnection();
    }
    
    protected DbConnectionProvider getConnectionProvider() {
        try {
            Field connectionProviderField = QueryManager.class.getDeclaredField("connectionProvider");
            connectionProviderField.setAccessible(true);
            
            DbConnectionProvider connectionProvider = (DbConnectionProvider) connectionProviderField.get(queryManager);
            
            return connectionProvider;
            
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Cannot retrieve connection provider.");
        }
    }
    
}
