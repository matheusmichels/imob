package model.com.imob.model.db.transactions;

import java.util.List;
import model.com.imob.model.data.User;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.Schema.Users;

/**
 *
 * @author matheus.michels
 */
public class UserManagerTransactions
{
    private static UserManagerTransactions transactions;
    
    public static UserManagerTransactions getInstance()
    {
        if ( transactions == null )
        {
            transactions = new UserManagerTransactions();
        }
        
        return transactions;
    }
    
    public void addUser( Database db, User user ) throws Exception
    {
        Users U = Users.table;
        
        String sql = "insert into " + U.name +
                     "("                      +
                        U.columns.NAME        + ", " +
                        U.columns.LOGIN       + ", " +
                        U.columns.PASSWORD    + ", " +
                        U.columns.EMAIL       + ", " +
                        U.columns.STATUS      +
                      ") values" +
                      "(" +
                      db.quote( user.getName() )      + ", " +
                      db.quote( user.getLogin() )     + ", " +
                      db.quote( user.getPassword() )  + ", " +
                      db.quote( user.getEmail() )     + ", " +
                      User.STATUS_ACTIVE              +
                      ")";
        
        db.executeCommand( sql );
    }
    
    public void updateUser( Database db, User user ) throws Exception
    {
        Users U = Users.table;
         
        String sql = "update " + U.name + " set " +
                     U.columns.NAME      + " = " + db.quote( user.getName() )      + ", " +
                     U.columns.LOGIN     + " = " + db.quote( user.getLogin() )     + ", " +
                     U.columns.PASSWORD  + " = " + db.quote( user.getPassword() )  + ", " +
                     U.columns.EMAIL     + " = " + db.quote( user.getEmail() )     +
                     " where " + U.columns.ID + " = " + user.getId();
                
        db.executeCommand( sql );
    }
    
    public void deleteUser( Database db, User user ) throws Exception
    {
        Users U = Users.table;
        
        String sql = "update " + U.name + " set " +
                     U.columns.STATUS  + " = " + User.STATUS_DELETED +
                     " where " + U.columns.ID + " = " + user.getId();
        
        db.executeCommand( sql );
    }
    
    public User getUserByLogin( Database db, String login, String password ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select +
                     " where " + U.columns.LOGIN + " = " + db.quote( login )     +
                     " and " + U.columns.PASSWORD + " = " + db.quote( password ) +
                     " and " + U.columns.STATUS + " != " + User.STATUS_DELETED;
                     
        return db.fetchOne( sql, U.fetcher );
    }
    
    public User getUser( Database db, int id ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select +
                     " where " + U.columns.ID + " = " + id;
                     
        return db.fetchOne( sql, U.fetcher );
    }
    
    public List<User> getUsers( Database db ) throws Exception
    {
        Users U = Users.table;
        
        String sql = U.select +
                     " where " + U.columns.STATUS + " = " + User.STATUS_ACTIVE +
                     " order by " + U.columns.ID;
        
        return db.fetchMany( sql, U.fetcher );
    }
}