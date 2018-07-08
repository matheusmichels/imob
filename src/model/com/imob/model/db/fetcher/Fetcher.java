package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;

/**
 *
 * @author matheus.michels
 * @param <T>
 */
public interface Fetcher<T> 
{
    public T fetch( ResultSet resultSet ) throws Exception;
}
