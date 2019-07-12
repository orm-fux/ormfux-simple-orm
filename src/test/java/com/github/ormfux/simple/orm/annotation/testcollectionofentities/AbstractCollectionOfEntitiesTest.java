package com.github.ormfux.simple.orm.annotation.testcollectionofentities;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.github.ormfux.simple.orm.annotation.AbstractAnnotationTest;
import com.github.ormfux.simple.orm.annotation.CollectionOfEntities;
import com.github.ormfux.simple.orm.annotation.Column;
import com.github.ormfux.simple.orm.annotation.Entity;
import com.github.ormfux.simple.orm.annotation.Id;
import com.github.ormfux.simple.orm.annotation.Version;
import com.github.ormfux.simple.orm.generators.RandomIdGenerator;

public abstract class AbstractCollectionOfEntitiesTest extends AbstractAnnotationTest {
    
    public AbstractCollectionOfEntitiesTest(String dbname) {
        super(dbname);
    }
    
    @Before
    @Override
    public void beforeTest() {
        super.beforeTest();
        
        queryManager.createQuery("create table mock (id varchar(255), version bigint)").executeUpdate();
        queryManager.createQuery("create table mock2 (id varchar(255), version bigint, mock varchar(255))").executeUpdate();
        queryManager.createQuery("create table mock1_mock2 (mocklink varchar(255), mock2link varchar(255))").executeUpdate();
        
    }
    
    @Entity(table = "mock")
    public static class MockEntity {
        
        @Column(columnName = "id", columnLabel = "id")
        @Id(RandomIdGenerator.class)
        private String id;
        
        @Column(columnName = "version", columnLabel = "version")
        @Version
        private long version;
        
        @CollectionOfEntities(joinColumn = "mock")
        private List<MockEntity2> mockList1 = new ArrayList<>();
        
        @CollectionOfEntities(joinTable = "mock1_mock2", joinColumn = "mocklink", inverseJoinColumn = "mock2link")
        private List<MockEntity2> mockList2 = new ArrayList<>();
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getId() {
            return id;
        }
        
        public void setVersion(long version) {
            this.version = version;
        }
        
        public long getVersion() {
            return version;
        }
        
        public List<MockEntity2> getMockList1() {
            return mockList1;
        }
        
        public void setMockList1(final List<MockEntity2> mockList1) {
            this.mockList1 = mockList1;
        }
        
        public List<MockEntity2> getMockList2() {
            return mockList2;
        }
        
        public void setMockList2(final List<MockEntity2> mockList2) {
            this.mockList2 = mockList2;
        }
    }
    
    @Entity(table = "mock2")
    public static class MockEntity2 {
        @Column(columnName = "id", columnLabel = "id")
        @Id(RandomIdGenerator.class)
        private String id;
        
        @Column(columnName = "version", columnLabel = "version")
        @Version
        private long version;
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getId() {
            return id;
        }
        
        public void setVersion(long version) {
            this.version = version;
        }
        
        public long getVersion() {
            return version;
        }
        
    }
}
