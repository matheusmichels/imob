/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.service;

import java.util.List;
import model.com.imob.model.data.Payment;
import model.com.imob.model.data.Rent;
import model.com.imob.model.data.Sale;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.transactions.FinanceManagerTransactions;
import view.com.imob.view.parts.Prompt;

/**
 *
 * @author Matheus Michels
 */
public class FinanceManagerService
{   
    private static FinanceManagerService service;
    private static FinanceManagerTransactions transactions;
    
    public static FinanceManagerService getInstance()
    {
        if ( service == null )
        {
            service = new FinanceManagerService();
        }
        
        return service;
    }
    
    public FinanceManagerService()
    {
        transactions = new FinanceManagerTransactions();
    }

    public void addSale( Sale sale ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addSale( db, sale );
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }
    
    public void addRent( Rent rent ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addRent( db, rent );
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }

    public void addPayments( Payment payment, int times ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.addPayments( db, payment, times );
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }
    
    public void payPlot( Payment payment ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            transactions.payPlot( db, payment );
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }
    
    public Sale getLastSale() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getLastSale( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Sale getSale( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getSale( db, id );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Sale> getSales() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getSales( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Payment> getRemainingPayments( int id, boolean sale ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getRemainingPayments( db, id, sale );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Payment> getPayments( int id, boolean sale ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPayments( db, id, sale );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Payment getPayment( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getPayment( db, id );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Rent getLastRent() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getLastRent( db  );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Rent getRent( int id ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getRent( db, id  );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<Rent> getRents() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getRents( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public Double countRemainingValue( int saleId ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.countRemainingValue( db, saleId );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public int getMinimumPlot( int id, boolean sale ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getMinimumPlot( db, id, sale );
        }
        
        finally
        {
            db.release();
        }
    }
}