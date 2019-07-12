package com.github.ormfux.simple.orm.query.testtypedquery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.ormfux.simple.orm.exception.NonMatchedParamException;
import com.github.ormfux.simple.orm.exception.NonUniqueResultException;
import com.github.ormfux.simple.orm.exception.SQLException;
import com.github.ormfux.simple.orm.query.TypedQuery;

public class GetSingleResultTest extends AbstractTypedQueryTest {
    
    public GetSingleResultTest() {
        super("singleresultdb");
    }

    @Test
    public void testWithoutLimitation() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class);
        MockEntity mock = typedQuery.getSingleResult();
        
        assertNotNull(mock);
        assertEquals("id", mock.getId());
    }
    
    @Test(expected = NonUniqueResultException.class)
    public void testMoreThanOneResult() {
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class);
        typedQuery.getSingleResult();
    }
    
    @Test
    public void testWithLimitation() {
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where mock.id = :id ");
        typedQuery.addParameter("id", "id");
        
        MockEntity mock = typedQuery.getSingleResult();
        
        assertNotNull(mock);
        assertEquals("id", mock.getId());
    }
    
    @Test
    public void testNoResult() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where 1<>1");
        
        MockEntity mock = typedQuery.getSingleResult();
        assertNull(mock);
    }
    
    @Test(expected = SQLException.class)
    public void testParamValueMissing() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where mock.id = :id ");
        
        typedQuery.getSingleResult();
    }
    
    @Test(expected = NonMatchedParamException.class)
    public void testTooManyParams() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where mock.id = :id ");
        typedQuery.addParameter("id", "id");
        typedQuery.addParameter("id2", "id");
        
        typedQuery.getSingleResult();
    }
    
}
