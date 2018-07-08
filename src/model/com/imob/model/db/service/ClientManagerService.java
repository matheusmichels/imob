package model.com.imob.model.db.service;

import java.util.List;
import model.com.imob.model.data.Client;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.transactions.ClientManagerTransactions;

/**
 *
 * @author Matheus Michels
 */
public class ClientManagerService
    implements
        ManagerService<Client>
{
    private static ClientManagerService service;
    private static ClientManagerTransactions transactions;
    
    public static ClientManagerService getInstance()
    {
        if ( service == null )
        {
            service = new ClientManagerService();
        }
        
        return service;
    }
    
    public ClientManagerService()
    {
        transactions = new ClientManagerTransactions();
    }

    @Override
    public void addValue( Client client ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addClient( db, client );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void updateValue( Client client ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updateClient( db, client );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( Client client ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deleteClient( db, client );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Client getValue( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getClient( db, id );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public List<Client> getValues() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getValues( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Client> getOwners() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getOwners( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Client> getBuyers() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getBuyers( db );
        }
        
        finally
        {
            db.release();
        }
    }
}
