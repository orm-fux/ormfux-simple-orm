package com.github.ormfux.simple.orm.query;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.github.ormfux.simple.orm.exception.DuplicateParamException;
import com.github.ormfux.simple.orm.exception.SQLException;
import com.github.ormfux.simple.orm.query.connection.DbConnectionProvider;

/**
 * Base class for queries.
 */
public abstract class AbstractQuery {
    
    /**
     * The provider for the database connection.
     */
    private final DbConnectionProvider dbConnectionProvider;
    
    /**
     * The query to execute.
     */
    private final String queryString;
    
    /**
     * The parameter values for the query.
     */
    private final Map<String, Object> queryParams = new HashMap<>();
    
    /**
     * Creates a new query.
     * 
     * @param dbConnection The connection to the database.
     * @param queryString The query to execute.
     */
    protected AbstractQuery(final DbConnectionProvider dbConnection, final String queryString) {
        this.dbConnectionProvider = Objects.requireNonNull(dbConnection);
        this.queryString = queryString;
    }
    
    /**
     * Adds a parameter to use for the query execution.
     * 
     * @param paramName The name of the parameter in the query.
     * @param value The parameter value.
     * 
     * @throws SQLException 
     */
    public final void addParameter(final String paramName, final Object value) throws SQLException {
        if (StringUtils.isBlank(paramName)) {
            throw new IllegalArgumentException("The parameter name is required.");
        } else if (queryParams.containsKey(paramName)) {
            throw new DuplicateParamException("A parameter with this name is already defined: " + paramName);
        } else {
            queryParams.put(paramName, value);
        }
    }
    
    /**
     * Adds multiple parameters to use for query execution.
     * 
     * @param parameters The parameters. Key is the parameter name and value, well, the value.
     */
    public final void addParameters(final Map<String, Object> parameters) throws SQLException {
        for (final Entry<String, Object> param : parameters.entrySet()) {
            addParameter(param.getKey(), param.getValue());
        }
    }
    
    /**
     * The database connection with which to execute the query.
     */
    protected Connection getDbConnection() {
        return dbConnectionProvider.getConnection();
    }
    
    /**
     * The provider for the database connection.
     */
    protected DbConnectionProvider getDbConnectionProvider() {
        return dbConnectionProvider;
    }
    
    /**
     * The query to execute.
     */
    protected String getQueryString() {
        return queryString;
    }
    
    /**
     * The parameter values for the query.
     */
    protected Map<String, Object> getQueryParams() {
        return queryParams;
    }
    
}
