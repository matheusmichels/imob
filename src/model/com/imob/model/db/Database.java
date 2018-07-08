package model.com.imob.model.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.com.imob.model.db.fetcher.Fetcher;

/**
 *
 * @author matheus.michels
 */
public class Database
{
    private static final String DB_DRIVER   = "org.postgresql.Driver";
    private static final String DB_URL      =  "jdbc:postgresql://localhost:5432/imobiliaria";
    private static final String DB_USER     = "postgres";
    private static final String DB_PASSWORD = "postgres";
    
    private static Database database;
    private static Connection connection;
    private static Statement statement;
    
    public static Database getInstance() throws Exception
    {
        if ( database == null )
        {
            database = new Database();
        }
        
        initConnection();
        
        return database;
    }
    
    private Database() throws Exception
    {
        DriverManager.registerDriver( (Driver) Class.forName( DB_DRIVER ).newInstance() );
    }
    
    private static void initConnection() throws Exception
    {
        if( connection == null || connection.isClosed() )
        {
            connection = DriverManager.getConnection( DB_URL, DB_USER, DB_PASSWORD );
        }
    }
    
    public void release() throws Exception
    {
        if ( connection != null && statement != null )
        {
            statement.close();
        }
    }
    
    public void executeCommand( String sql ) throws Exception
    {
        if ( connection != null )
        {
            statement = connection.createStatement();
            
            statement.execute( sql );
            
            statement.close();
        }
    }
    
    public <T> T fetchOne( String sql, Fetcher<T> fetcher ) throws Exception, Exception
    {
        statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery( sql );
        
        T t = null;
        
        if ( resultSet.next() )
        {
            t = fetcher.fetch( resultSet );
        }
        
        resultSet.close();

        statement.close();
        
        return t;
    }
    
    public <T>  List<T> fetchMany( String sql, Fetcher<T> fetcher ) throws Exception
    {
        statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery( sql );
        
        List<T> list = new ArrayList();
        
        while ( resultSet.next() )
        {
            list.add( fetcher.fetch( resultSet ) );
        }
        
        resultSet.close();
        
        statement.close();

        return list;
    }
    
    public int queryInteger( String sql ) throws Exception
    {
        int result = 0;
        int i = 1;
        
        statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery( sql );
        
        while ( resultSet.next() )
        {
            result = resultSet.getInt( i );
        }
        
        resultSet.close();
        
        statement.close();

        return result;
    }
    
    public Double queryDouble( String sql ) throws Exception
    {
        Double result = 0.0;
        int i = 1;
        
        statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery( sql );
        
        while ( resultSet.next() )
        {
            result = resultSet.getDouble( i );
        }
        
        resultSet.close();
        
        statement.close();

        return result;
    }
    
    public String quote( Date date )
    {
        if( date == null )
        {
            return "null";
        }
    
        return "\'" + date.toString()  + "\'";
    }
    
    public String quote( String sql )
    {
        if( sql == null )
        {
           return "null";
        }
        
        if( sql.contains( "\'" ) )
        {
            sql = sql.replace( "\'", "\''" );
        }
        
        return "\'" + sql + "\'";
    }
}