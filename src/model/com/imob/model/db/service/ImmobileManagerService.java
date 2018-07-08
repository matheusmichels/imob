package model.com.imob.model.db.service;

import java.util.List;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.transactions.ImmobileManagerTransactions;

/**
 *
 * @author matheus.michels
 */
public class ImmobileManagerService
    implements
        ManagerService<Immobile>
{
    private static ImmobileManagerService service;
    private static ImmobileManagerTransactions transactions;
    
    public static ImmobileManagerService getInstance()
    {
        if ( service == null )
        {
            service = new ImmobileManagerService();
        }
        
        return service;
    }
    
    private ImmobileManagerService()
    {
        transactions = new ImmobileManagerTransactions();
    }
    
    @Override
    public void addValue( Immobile immobile ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addImmobile( db, immobile );
        }
        
        finally
        {
            db.release();
        }
    }
    
    @Override
    public void updateValue( Immobile object ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.updateImmobile( db, object );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public void deleteValue( Immobile object ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.deleteImmobile( db, object );
        }
        
        finally
        {
            db.release();
        }
    }

    @Override
    public Immobile getValue(int id) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getImmobile( db, id );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Immobile> getValues() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getImmobiles( db );
        }
        
        finally
        {
            db.release();
        }
    }
}
