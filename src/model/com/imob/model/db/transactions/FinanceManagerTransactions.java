/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.transactions;

import java.sql.Date;
import java.util.List;
import model.com.imob.model.data.Payment;
import model.com.imob.model.data.Rent;
import model.com.imob.model.data.Sale;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.Schema.Payments;
import model.com.imob.model.db.Schema.Rents;
import model.com.imob.model.db.Schema.Sales;

/**
 *
 * @author Matheus Michels
 */
public class FinanceManagerTransactions
{
    private static FinanceManagerTransactions transactions;
    
    public static FinanceManagerTransactions getInstance()
    {
        if ( transactions == null )
        {
            transactions = new FinanceManagerTransactions();
        }
        
        return transactions;
    }
    
    public void addSale( Database db, Sale sale ) throws Exception
    {
        Sales S = Sales.table;
        
        String sql = "insert into " + S.name  +
                    "("                       +
                        S.columns.REF_IMMOBILE + ", "  +
                        S.columns.REF_OWNER    + ", "  +
                        S.columns.REF_BUYER    + ", "  +
                        S.columns.VALUE        + ", "  +
                        S.columns.PLOTS        + ", "  +
                        S.columns.PROFIT       + ", "  +
                        S.columns.DESCRIPTION  + ", "  +
                        S.columns.DT_SALE      +
                    ") values " +
                    "(" +
                        sale.getImmobileId()              + ", " +
                        sale.getOwnerId()                 + ", " +
                        sale.getBuyerId()                 + ", " +
                        sale.getValue()                   + ", " +
                        sale.getPlots()                   + ", " +
                        sale.getProfit()                  + ", " +
                        db.quote( sale.getDescription() ) + ", " +
                        db.quote( sale.getDate() )        +
                    ")";
        
        db.executeCommand( sql );
    }
    
    public void addRent( Database db, Rent rent ) throws Exception
    {
        Rents R = Rents.table;
        
        String sql = "insert into " + R.name  +
                    "("                       +
                        R.columns.REF_IMMOBILE + ", "  +
                        R.columns.REF_OWNER    + ", "  +
                        R.columns.REF_RENTER   + ", "  +
                        R.columns.VALUE        + ", "  +
                        R.columns.MONTHS       + ", "  +
                        R.columns.PROFIT       + ", "  +
                        R.columns.DESCRIPTION  + ", "  +
                        R.columns.DT_RENT      +
                    ") values " +
                    "(" +
                        rent.getImmobileId()              + ", " +
                        rent.getOwnerId()                 + ", " +
                        rent.getRenterId()                + ", " +
                        rent.getValue()                   + ", " +
                        rent.getMonths()                  + ", " +
                        rent.getProfit()                  + ", " +
                        db.quote( rent.getDescription() ) + ", " +
                        db.quote( rent.getDate() )        +
                    ")";
        
        db.executeCommand( sql );
    }
    
    public void addPayments( Database db, Payment payment, int times ) throws Exception
    {
        if ( times > 0 )
        {
            Payments P = Payments.table;

            String sql = "insert into " + P.name +
                        "(" +
                            P.columns.SEQUENCE   + ", "   +
                            P.columns.REF_RENT   + ", "   +
                            P.columns.REF_SALE   + ", "   +
                            P.columns.VALUE      + ", "   +
                            P.columns.DT_PAYMENT +
                        ") values";
            
            for ( int i = 1; i <= times; i++ )
            {
                sql += " (" +
                            i                   + ", "   +
                            payment.getRentId() + ", "   +
                            payment.getSaleId() + ", "   +
                            payment.getValue()  + ", "   +
                            null                         +
                        "),";
            }
                   
            sql = sql.substring( 0, sql.lastIndexOf( "," ) );

            db.executeCommand( sql );
        }
    }
    
    public void payPlot( Database db, Payment payment ) throws Exception
    {
        Payments P = Payments.table;
        
        Date now = new Date( System.currentTimeMillis() );
        
        String sql = "update " + P.name + " set " +
                     P.columns.DT_PAYMENT     + " = " + db.quote( now ) +
                     " where " + P.columns.ID + " = " + payment.getId();
        
        db.executeCommand( sql );
    }
    
    public Sale getLastSale( Database db ) throws Exception
    {
        Sales S = Sales.table;
        
        String sql = S.select +
                    " order by " + S.columns.ID + " desc limit 1";
        
        return db.fetchOne( sql, S.fetcher );
    }
    
    public Sale getSale( Database db, int id ) throws Exception
    {
        Sales S = Sales.table;
        
        String sql = S.select +
                " where " + S.columns.ID + " = " + id;
        
        return db.fetchOne( sql, S.fetcher );
    }
    
    public List<Sale> getSales( Database db ) throws Exception
    {
        Sales S = Sales.table;
        
        String sql = S.select +
                    " order by " + S.columns.ID + " desc";
        
        return db.fetchMany( sql, S.fetcher );
    }
    
    public List<Payment> getRemainingPayments( Database db, int id, boolean sale ) throws Exception
    {
        Payments P = Payments.table;
        
        String sql = P.select +
                " where " + ( sale ? P.columns.REF_SALE : P.columns.REF_RENT ) + " = " + id +
                " and " + P.columns.DT_PAYMENT + " is null";
        
        return db.fetchMany( sql, P.fetcher );
    }
    
    public Payment getPayment( Database db, int id ) throws Exception
    {
        Payments P = Payments.table;
        
        String sql = P.select +
                " where " + P.columns.ID + " = " + id;
        
        return db.fetchOne( sql, P.fetcher );
    }
    
    public List<Payment> getPayments( Database db, int id, boolean sale ) throws Exception
    {
        Payments P = Payments.table;
        
        String sql = P.select +
                " where " + ( sale ? P.columns.REF_SALE : P.columns.REF_RENT ) + " = " + id;
        
        return db.fetchMany( sql, P.fetcher );
    }
    
    public Rent getLastRent( Database db ) throws Exception
    {
        Rents R = Rents.table;
        
        String sql = R.select +
                " order by " + R.columns.ID + " desc limit 1";
        
        return db.fetchOne( sql, R.fetcher );
    }
    
    public Rent getRent( Database db, int id ) throws Exception
    {
        Rents R = Rents.table;
        
        String sql = R.select +
                " where " + R.columns.ID + " = " + id;
        
        return db.fetchOne( sql, R.fetcher );
    }
    
    public List<Rent> getRents( Database db ) throws Exception
    {
        Rents R = Rents.table;
        
        String sql = R.select +
                " order by " + R.columns.ID;
        
        return db.fetchMany( sql, R.fetcher );
    }
    
    public Double countRemainingValue( Database db, int saleId ) throws Exception
    {
        Payments P = Payments.table;
        
        String sql = "select sum(" + P.columns.VALUE + ")" +
                " from " + P.name +
                " where " + P.columns.REF_SALE + " = " + saleId +
                " and " + P.columns.DT_PAYMENT + " is null";

        return db.queryDouble( sql );
    }
    
    public int getMinimumPlot( Database db, int id, boolean sale ) throws Exception
    {
        Payments P = Payments.table;
        
        String sql = "select min(" + P.columns.ID + ")" +
                    " from " + P.name +
                    " where " + P.columns.DT_PAYMENT + " is null " +
                    " and " + ( sale ? P.columns.REF_SALE : P.columns.REF_RENT ) + " = " + id;
        
        return db.queryInteger(sql);
    }
}
