package model.com.imob.model.db.transactions;

import java.util.List;
import model.com.imob.model.data.City;
import model.com.imob.model.data.State;
import model.com.imob.model.db.Database;
import model.com.imob.model.db.Schema.Cities;
import model.com.imob.model.db.Schema.States;

/**
 *
 * @author Matheus Michels
 */
public class LocalManagerTransactions
{
    private static LocalManagerTransactions transactions;
    
    public static LocalManagerTransactions getInstance()
    {
        return transactions;
    }
    
    public List<State> getStates( Database db ) throws Exception
    {
        States S = States.table;
        
        String sql = S.select +
                     " order by " + S.columns.NAME;
        
        return db.fetchMany( sql, S.fetcher );
    }
    
    public List<City> getCities( Database db, int stateId ) throws Exception
    {
        Cities C = Cities.table;
        
        String sql = C.select +
                     " where " + C.columns.REF_STATE + " = " + stateId +
                     " order by " + C.columns.NAME;
        
        return db.fetchMany( sql, C.fetcher );
    }
     
    public City getCity( Database db, int cityId ) throws Exception
    {
        Cities C = Cities.table;
        
        String sql = C.select +
                         " where " + C.columns.ID + " = " + cityId;
        
        return db.fetchOne( sql, C.fetcher );
    }
    
    public State getState( Database db, int stateId ) throws Exception
    {
        States S = States.table;
        
        String sql = S.select +
                     " where " + S.columns.ID + " = " + stateId;
        
        return db.fetchOne( sql, S.fetcher );
    }
}
