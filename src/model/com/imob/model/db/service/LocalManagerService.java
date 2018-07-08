/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.service;

import java.util.List;
import model.com.imob.model.data.City;
import model.com.imob.model.data.State;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.transactions.LocalManagerTransactions;

/**
 *
 * @author Matheus Michels
 */
public class LocalManagerService
{
    private static LocalManagerService service;
    private static LocalManagerTransactions transactions;
    
    public static LocalManagerService getInstance()
    {
        if ( service == null )
        {
            service = new LocalManagerService();
        }
        
        return service;
    }
    
    public LocalManagerService()
    {
        transactions = new LocalManagerTransactions();
    }

    public List<State> getStates() throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getStates( db );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public List<City> getCities( int stateId ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getCities( db, stateId );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public City getCity( int cityId ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getCity( db, cityId );
        }
        
        finally
        {
            db.release();
        }
    }
    
    public State getState( int stateId ) throws Exception
    {
        Database db = Database.getInstance();
        
        try
        {
            return transactions.getState( db, stateId );
        }
        
        finally
        {
            db.release();
        }
    }
}