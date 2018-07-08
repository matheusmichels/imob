package model.com.imob.model.db.service;

import java.util.List;

/**
 *
 * @author Matheus Michels
 */
public interface ManagerService<T>
{
    public void addValue( T object ) throws Exception;
    public void updateValue( T object ) throws Exception;
    public void deleteValue( T object ) throws Exception;
    public T getValue( int id ) throws Exception;
    public List<T> getValues() throws Exception;
}