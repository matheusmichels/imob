package model.com.imob.model.db.service;

import java.util.List;
import model.com.imob.model.data.User;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.transactions.UserManagerTransactions;

/**
 *
 * @author matheus.michels
 */
public class UserManagerService
    implements ManagerService<User>
{
    private static UserManagerService service;
    private static UserManagerTransactions transactions;
    
    public static UserManagerService getInstance()
    {
        if ( service == null )
        {
            service = new UserManagerService();
        }
        
        return service;
    }
    
    private UserManagerService()
    {
        transactions = new UserManagerTransactions();
    }
    
    public void addValue( User user ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addUser( db, user );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public void updateValue( User user ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updateUser( db, user );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public void deleteValue( User user ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deleteUser( db, user );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public User getValue( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getUser( db, id );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<User> getValues() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getUsers( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public User getUserByLogin( String login, String password ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getUserByLogin( db, login, password );
        }
        
        finally
        {
            db.release();
        }
    }
}
