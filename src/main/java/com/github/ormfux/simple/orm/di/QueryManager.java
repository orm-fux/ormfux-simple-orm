package com.github.ormfux.simple.orm.di;

import com.github.ormfux.simple.di.annotations.Bean;
import com.github.ormfux.simple.di.annotations.BeanConstructor;
import com.github.ormfux.simple.di.annotations.ConfigValue;
import com.github.ormfux.simple.orm.query.Query;
import com.github.ormfux.simple.orm.query.TypedQuery;
import com.github.ormfux.simple.orm.query.connection.AbstractDbConnectionProvider;
import com.github.ormfux.simple.orm.query.connection.DbConnectionProvider;


/**
 * A QueryManager that can be used as an injectable "service". By defining this separately 
 * we keep the DI dependency optional.
 */
@Bean 
public class QueryManager {
    
    /**
     * The actual manager for query handling.
     */
    private final com.github.ormfux.simple.orm.query.QueryManager wrappedManager = new com.github.ormfux.simple.orm.query.QueryManager();
    
    /**
     * @param connectionProviderType The type of provider for the database connection.
     * @param databaseUrl The URL to the database.
     * @param connectionParams Parameters for the connection.
     */
    @BeanConstructor
    public QueryManager(@ConfigValue("com.github.ormfux.querymanager.connection_provider_type") final Class<? extends AbstractDbConnectionProvider> connectionProviderType, 
                        @ConfigValue("com.github.ormfux.querymanager.database_url") final String databaseUrl, 
                        @ConfigValue("com.github.ormfux.querymanager.connection_params") final String... connectionParams) {
        wrappedManager.setDatabase(connectionProviderType, databaseUrl, connectionParams);
    }
    
    /**
     * Creates a new query for this manager's database.
     *
     * @param queryString The query.
     */
    public Query createQuery(final String queryString) {
        return wrappedManager.createQuery(queryString);
    }
    
    /**
     * Creates a new "select all" query for the entity type. The type must be annotated with {@code @Entity}
     * 
     * @param entityType The type of entity to query.
     */
    public <T> TypedQuery<T> createQuery(final Class<T> entityType) {
        return wrappedManager.createQuery(entityType);
    }
    
    /**
     * Creates a new query for the entity type. Adds the suffix to the query. The suffix can consist of joins, 
     * where conditions, etc.
     * 
     * @param entityType The type of entity to query.
     * @param querySuffix The suffix for the query.
     */
    public <T> TypedQuery<T> createQuery(final Class<T> entityType, final String querySuffix) {
        return wrappedManager.createQuery(entityType, querySuffix);
    }
    
    /**
     * Creates a new query for the entity type. Adds the suffix to the query. The suffix can consist of joins, 
     * where conditions, etc.
     * 
     * @param entityType The type of entity to query.
     * @param querySuffix The suffix for the query.
     * @param entityAlias Alias for the entity in the query when the auto-generated one should not be used.
     */
    public <T> TypedQuery<T> createQuery(final Class<T> entityType, final String querySuffix, final String entityAlias) {
        return wrappedManager.createQuery(entityType, querySuffix, entityAlias);
    }
    
    /**
     * If the underlying {@link DbConnectionProvider} can create a database backup.
     */
    public boolean isCanBackupDatabase() {
        return wrappedManager.isCanBackupDatabase();
    }
    
    /**
     * Uses the underlying {@link DbConnectionProvider} to create a database backup.
     * 
     * @param databaseVersion A version indicator for the backed up database.
     */
    public void backupDatabase(final CharSequence databaseVersion) {
        wrappedManager.backupDatabase(databaseVersion);
    }
    
    /**
     * Checks, if the database is reachable.
     * 
     * @return {@code true} when reachable.
     */
    public boolean pingDatabase() {
        return wrappedManager.pingDatabase();
    }
}
