package com.github.ormfux.simple.orm.query.connection;

import java.sql.Connection;

/**
 * Interface for providers, which build connections to a database.
 */
public interface DbConnectionProvider {
    
    /**
     * A connection to the database.
     */
    public abstract Connection getConnection();
    
    /**
     * Closes all currently open database connections.
     */
    public abstract void closeAllConnections();
    
    /**
     * If the connection provider can back up the database to which it connects.
     */
    public boolean isCanBackupDatabase();
    
    /**
     * Backs up the database.
     * 
     * @param databaseVersion A version indicator for the backed up database.
     */
    public void backupDatabase(final CharSequence databaseVersion);
    
    /**
     * Checks, if the database is reachable.
     * 
     * @return {@code true} when reachable.
     */
    public boolean ping();
}
