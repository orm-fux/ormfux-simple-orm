package com.github.ormfux.simple.orm.query.testquery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.ormfux.simple.orm.exception.NonMatchedParamException;
import com.github.ormfux.simple.orm.exception.NonUniqueResultException;
import com.github.ormfux.simple.orm.exception.SQLException;
import com.github.ormfux.simple.orm.query.Query;
import com.github.ormfux.simple.orm.query.QueryResult.QueryResultRow;

public class GetSingleResultTest extends AbstractQueryTest {
    
    public GetSingleResultTest() {
        super("nativesingleresultdb");
    }

    @Test
    public void testWithoutLimitation() {
        Query query = queryManager.createQuery("select id from mock");
        QueryResultRow result = query.getSingleResult();
        
        assertNotNull(result);
        assertEquals("idvalue", result.getValue(0));
        assertEquals("idvalue", result.getValue("id"));
    }
    
    @Test(expected = NonUniqueResultException.class)
    public void testMoreThanOneSingleResult() {
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        Query query = queryManager.createQuery("select id from mock");
        query.getSingleResult();
    }
    
    @Test
    public void testWithLimitation() {
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        Query query = queryManager.createQuery("select id from mock where mock.id = :id ");
        query.addParameter("id", "idvalue");
        
        QueryResultRow result = query.getSingleResult();
        
        assertNotNull(result);
        assertEquals("idvalue", result.getValue(0));
        assertEquals("idvalue", result.getValue("id"));
    }
    
    @Test
    public void testNoResult() {
        Query query = queryManager.createQuery("select id from mock where 1<>1");
        
        assertNull(query.getSingleResult());
    }
    
    @Test(expected = SQLException.class)
    public void testParamValueMissing() {
        Query query = queryManager.createQuery("select id from mock where mock.id = :id ");
        
        query.getSingleResult();
    }
    
    @Test(expected = NonMatchedParamException.class)
    public void testTooManyParams() {
        Query query = queryManager.createQuery("select id from where mock.id = :id ");
        query.addParameter("id", "id");
        query.addParameter("id2", "id");
        
        query.getSingleResult();
    }
    
}
