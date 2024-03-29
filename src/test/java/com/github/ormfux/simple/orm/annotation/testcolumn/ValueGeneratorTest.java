package com.github.ormfux.simple.orm.annotation.testcolumn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.github.ormfux.simple.orm.annotation.AbstractAnnotationTest;
import com.github.ormfux.simple.orm.annotation.Column;
import com.github.ormfux.simple.orm.annotation.Entity;
import com.github.ormfux.simple.orm.annotation.Id;
import com.github.ormfux.simple.orm.annotation.Version;
import com.github.ormfux.simple.orm.generators.RandomIdGenerator;
import com.github.ormfux.simple.orm.generators.ValueGenerator;
import com.github.ormfux.simple.orm.query.TypedQuery;

public class ValueGeneratorTest extends AbstractAnnotationTest {
    
    public ValueGeneratorTest() {
        super("valuegeneratordb");
    }
    
    @Before
    public void beforeTest() {
        super.beforeTest();
        
        queryManager.createQuery("create table valuegenentity (id varchar(255) not null, version bigint, genvalue varchar(255))").executeUpdate();
        queryManager.createQuery("insert into valuegenentity (id, version) values ('idvalue', 0)").executeUpdate();
    }
    
    @Test
    public void testCreate() {
        GenValueEntity entity = new GenValueEntity();
        
        TypedQuery<GenValueEntity> query = queryManager.createQuery(GenValueEntity.class);
        query.update(entity);
        assertEquals("generated value", entity.getGenProperty());
        
        GenValueEntity loadedEntity = query.load(entity.getId());
        assertNotNull(loadedEntity);
        assertEquals("generated value", loadedEntity.getGenProperty());
    }

    @Test
    public void testUpdate() {
        GenValueEntity entity = new GenValueEntity();
        entity.setId("idvalue");
        
        TypedQuery<GenValueEntity> query = queryManager.createQuery(GenValueEntity.class);
        query.update(entity);
        assertEquals("generated value", entity.getGenProperty());
        
        GenValueEntity loadedEntity = query.load(entity.getId());
        assertNotNull(loadedEntity);
        assertEquals("generated value", loadedEntity.getGenProperty());
    }
    
    @Entity(table = "valuegenentity")
    public static class GenValueEntity {
        
        @Column(columnName = "id", columnLabel = "id")
        @Id(RandomIdGenerator.class)
        private String id;
        
        @Column(columnName = "version", columnLabel = "version")
        @Version
        private long version;
        
        @Column(columnName = "genvalue", columnLabel = "genvalue", generator = Generator.class)
        private String genProperty;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        
        public long getVersion() {
            return version;
        }
        
        public void setVersion(long version) {
            this.version = version;
        }
        
        
        public String getGenProperty() {
            return genProperty;
        }
        
        public void setGenProperty(String genProperty) {
            this.genProperty = genProperty;
        }
        
    }
    
    public static class Generator implements ValueGenerator<String> {

        @Override
        public String generate(Object previousValue) {
            return "generated value";
        }
        
    }
}
