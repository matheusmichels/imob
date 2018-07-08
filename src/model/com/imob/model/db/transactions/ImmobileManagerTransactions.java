package model.com.imob.model.db.transactions;

import java.util.List;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.Schema.Immobiles;

/**
 *
 * @author matheus.michels
 */
public class ImmobileManagerTransactions
{
    private static ImmobileManagerTransactions transactions;
    
    public ImmobileManagerTransactions getInstance()
    {
        return transactions;
    }
    
    public void addImmobile( Database db, Immobile immobile ) throws Exception
    {
        Immobiles I = Immobiles.table;
        
        String sql = "insert into " + I.name +
                     "("                      +
                        I.columns.NUMBER        + ", " +
                        I.columns.COMPLEMENT    + ", " +
                        I.columns.STREET        + ", " +
                        I.columns.DISTRICT      + ", " +
                        I.columns.REF_CITY      + ", " +
                        I.columns.CEP           + ", " +
                        I.columns.VALUE         + ", " +
                        I.columns.RENT          + ", " +
                        I.columns.DESCRIPTION   + ", " +
                        I.columns.PURPOSE       + ", " +
                        I.columns.REF_OWNER     + ", " +
                        I.columns.REF_BUYER     + ", " +
                        I.columns.STATUS        +
                      ") values" +
                      "(" +
                      immobile.getNumber()                  + ", " +
                      db.quote( immobile.getComplement() )  + ", " +
                      db.quote( immobile.getStreet() )      + ", " +
                      db.quote( immobile.getDistrict() )    + ", " +
                      immobile.getCityId()                  + ", " +
                      db.quote( immobile.getCep() )         + ", " +
                      immobile.getValue()                   + ", " +
                      immobile.getRent()                    + ", " +
                      db.quote( immobile.getDescription() ) + ", " +
                      immobile.getPurpose()                 + ", " +
                      immobile.getOwnerId()                 + ", " +
                      immobile.getBuyerId()                 + ", " +
                      Immobile.STATUS_AVAIABLE              +
                      ")";
        
        db.executeCommand( sql );
    }
    
    
    public void updateImmobile( Database db, Immobile immobile ) throws Exception
    {
        Immobiles I = Immobiles.table;
        
        String sql = " update " + I.name       + " set " +
                        I.columns.NUMBER       + " = " + immobile.getNumber()                  + ", " +
                        I.columns.STREET       + " = " + db.quote( immobile.getStreet() )      + ", " +
                        I.columns.DISTRICT     + " = " + db.quote( immobile.getDistrict() )    + ", " +
                        I.columns.REF_CITY     + " = " + immobile.getCityId()                  + ", " +
                        I.columns.CEP          + " = " + db.quote( immobile.getCep() )         + ", " +
                        I.columns.VALUE        + " = " + immobile.getValue()                   + ", " +
                        I.columns.RENT         + " = " + immobile.getRent()                    + ", " +
                        I.columns.DESCRIPTION  + " = " + db.quote( immobile.getDescription() ) + ", " +
                        I.columns.PURPOSE      + " = " + immobile.getPurpose()                 + ", " +
                        I.columns.STATUS       + " = " + immobile.getStatus()                   + ", " +
                        I.columns.REF_BUYER    + " = " + immobile.getBuyerId()                  + ", " +
                        I.columns.REF_OWNER    + " = " + immobile.getOwnerId()                 +
                     " where " + 
                        I.columns.ID           + " = " + immobile.getId();
        
        db.executeCommand( sql );
    }
    
    public void deleteImmobile( Database db, Immobile immobile ) throws Exception
    {
        Immobiles I = Immobiles.table;
        
        String sql = " update " + I.name       + " set " +
                        I.columns.STATUS       + " = " + Immobile.STATUS_DELETED  +
                     " where " + 
                        I.columns.ID           + " = " + immobile.getId();
        
        db.executeCommand( sql );
    }
    
    public List<Immobile> getImmobilesByCity( Database db, int cityId ) throws Exception
    {
        Immobiles I = Immobiles.table;
        
        String sql = I.select
                   + " where " + I.columns.REF_CITY + " = " + cityId;
        
        return db.fetchMany( sql, I.fetcher );
    }
    
    public List<Immobile> getImmobiles( Database db ) throws Exception
    {
        Immobiles I = Immobiles.table;
        
        String sql = I.select
                   + " where " + I.columns.STATUS + " != " + Immobile.STATUS_DELETED
                   + " order by " + I.columns.ID;
        
        return db.fetchMany( sql, I.fetcher );
    }
    
    public Immobile getImmobile( Database db, int id ) throws Exception
    {
        Immobiles I = Immobiles.table;
        
        String sql = I.select
                    + " where " + I.columns.STATUS + " != " + Immobile.STATUS_DELETED
                   + " and " + I.columns.ID + " = " + id;
        
        return db.fetchOne( sql, I.fetcher );
    }
}