package com.github.ormfux.simple.orm.query.testtypedquery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.github.ormfux.common.utils.ListUtils;
import com.github.ormfux.simple.orm.exception.NonMatchedParamException;
import com.github.ormfux.simple.orm.exception.SQLException;
import com.github.ormfux.simple.orm.query.TypedQuery;

public class GetResultListTest extends AbstractTypedQueryTest {
    
    public GetResultListTest() {
        super("resultlistdb");
    }

    @Test
    public void testWithoutLimitation() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class);
        List<MockEntity> resultList = typedQuery.getResultList();
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals("id", resultList.get(0).getId());
        
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        resultList = typedQuery.getResultList();
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertTrue(ListUtils.exists(resultList, mock -> "id".equals(mock.getId())));
        assertTrue(ListUtils.exists(resultList, mock -> "id2".equals(mock.getId())));
        
    }
    
    @Test
    public void testWithLimitation() {
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where id = :id");
        typedQuery.addParameter("id", "id");
        
        List<MockEntity> resultList = typedQuery.getResultList();
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals("id", resultList.get(0).getId());
    }
    
    @Test
    public void testWithLimitationNoResult() {
        queryManager.createQuery("insert into mock (id) values ('id2')").executeUpdate();
        
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where id = :id");
        typedQuery.addParameter("id", "nonexisting");
        
        List<MockEntity> resultList = typedQuery.getResultList();
        assertNotNull(resultList);
        assertEquals(0, resultList.size());
    }
    
    @Test(expected = SQLException.class)
    public void testParamValueMissing() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where mock.id = :id ");
        
        typedQuery.getResultList();
    }
    
    @Test(expected = NonMatchedParamException.class)
    public void testTooManyParams() {
        TypedQuery<MockEntity> typedQuery = queryManager.createQuery(MockEntity.class, "where mock.id = :id ");
        typedQuery.addParameter("id", "id");
        typedQuery.addParameter("id2", "id");
        
        typedQuery.getResultList();
    }
    
}
