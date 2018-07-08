/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.transactions;

import java.util.List;
import model.com.imob.model.data.Client;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.Schema;
import model.com.imob.model.db.Schema.Clients;

/**
 *
 * @author Matheus Michels
 */
public class ClientManagerTransactions
{
    private static ClientManagerTransactions transactions;
    
    public static ClientManagerTransactions getInstance()
    {
        if ( transactions == null )
        {
            transactions = new ClientManagerTransactions();
        }
        
        return transactions;
    }
    
    public void addClient( Database db, Client client ) throws Exception
    {
        Clients C = Clients.table;
        
        String sql = "insert into " + C.name    +
                     "("                        +
                        C.columns.NAME          + ", " +
                        C.columns.NUMBER        + ", " +
                        C.columns.COMPLEMENT    + ", " +
                        C.columns.STREET        + ", " +
                        C.columns.DISTRICT      + ", " +
                        C.columns.REF_CITY      + ", " +
                        C.columns.CEP           + ", " +
                        C.columns.EMAIL         + ", " +
                        C.columns.RG            + ", " +
                        C.columns.CPF           + ", " +
                        C.columns.CNPJ          + ", " +
                        C.columns.PHONE         + ", " +
                        C.columns.CELLULAR      + ", " +
                        C.columns.TYPE          + ", " +
                        C.columns.STATUS        +
                      ") values" +
                      "(" +
                      db.quote( client.getName() )        + ", " +
                      client.getNumber()                  + ", " +
                      db.quote( client.getComplement() )  + ", " +
                      db.quote( client.getStreet() )      + ", " +
                      db.quote( client.getDistrict() )    + ", " +
                      client.getCityId()    + ", " +
                      db.quote( client.getCep() )         + ", " +
                      db.quote( client.getEmail() )       + ", " +
                      db.quote( client.getRg() )          + ", " +
                      db.quote( client.getCpf() )         + ", " +
                      db.quote( client.getCnpj() )        + ", " +
                      db.quote( client.getPhone() )       + ", " +
                      db.quote( client.getCellular() )    + ", " +
                      client.getType()                    + ", " +
                      Client.STATUS_ACTIVE                +
                      ")";
        
        db.executeCommand( sql );
    }
    
    public void updateClient( Database db, Client client ) throws Exception
    {
        Schema.Clients C = Schema.Clients.table;
        
        String sql = " update " + C.name       + " set " +
                        C.columns.NAME         + " = " + db.quote( client.getName() )           + ", " +
                        C.columns.NUMBER       + " = " + client.getNumber()                     + ", " +
                        C.columns.COMPLEMENT   + " = " + db.quote( client.getComplement() )     + ", " +
                        C.columns.STREET       + " = " + db.quote( client.getStreet() )         + ", " +
                        C.columns.DISTRICT     + " = " + db.quote( client.getDistrict() )       + ", " +
                        C.columns.REF_CITY     + " = " + client.getCityId()                     + ", " +
                        C.columns.CEP          + " = " + db.quote( client.getCep() )            + ", " +
                        C.columns.EMAIL        + " = " + db.quote( client.getEmail() )          + ", " +
                        C.columns.RG           + " = " + db.quote( client.getRg() )             + ", " +
                        C.columns.CNPJ         + " = " + db.quote( client.getCnpj() )           + ", " +
                        C.columns.CPF          + " = " + db.quote( client.getCpf() )            + ", " +
                        C.columns.PHONE        + " = " + db.quote( client.getPhone() )          + ", " +
                        C.columns.CELLULAR     + " = " + db.quote( client.getCellular() )       + ", " +
                        C.columns.TYPE         + " = " + client.getType()                       +
                     " where " + 
                        C.columns.ID           + " = " + client.getId();
        
        db.executeCommand( sql );
    }
    
    public void deleteClient( Database db, Client client ) throws Exception
    {
        Schema.Clients C = Schema.Clients.table;
        
        String sql = " update " + C.name    + " set " +
                        C.columns.STATUS    + " = " + Client.STATUS_DELETED  +
                     " where " + 
                        C.columns.ID        + " = " + client.getId();
        
        db.executeCommand( sql );
    }
    
    public Client getClient( Database db, int id ) throws Exception
    {
        Clients C = Clients.table;
        
        String sql = C.select +
                     " where " + C.columns.ID + " = " + id;
        
        return db.fetchOne( sql, C.fetcher );
    }
    
    public List<Client> getValues( Database db ) throws Exception
    {
        Clients C = Clients.table;
        
        String sql = C.select +
                    " where " + C.columns.STATUS + " = " + Client.STATUS_ACTIVE;
        
        return db.fetchMany( sql, C.fetcher );
    }
    
    public List<Client> getOwners( Database db ) throws Exception
    {
        Clients C = Clients.table;
        
        String sql = C.select +
                     " where " + C.columns.STATUS + " = " + Client.STATUS_ACTIVE +
                     " and (" + C.columns.TYPE + " = " + Client.TYPE_SELLER +
                     " or "    + C.columns.TYPE + " = " + Client.TYPE_ALL + ")";
        
        return db.fetchMany( sql, C.fetcher );
    }
    
    public List<Client> getBuyers( Database db ) throws Exception
    {
        Clients C = Clients.table;
        
        String sql = C.select +
                     " where " + C.columns.STATUS + " = " + Client.STATUS_ACTIVE +
                     " and (" + C.columns.TYPE + " = " + Client.TYPE_BUYER +
                     " or "    + C.columns.TYPE + " = " + Client.TYPE_ALL + ")";
        
        return db.fetchMany( sql, C.fetcher );
    }
}
