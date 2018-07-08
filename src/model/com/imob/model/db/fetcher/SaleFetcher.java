package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.Sale;

/**
 *
 * @author Matheus Michels
 */
public class SaleFetcher
    implements
        Fetcher<Sale>
{
    @Override
    public Sale fetch( ResultSet resultSet ) throws Exception
    {
        int i = 1;
        
        Sale sale = new Sale();
        
        sale.setId( resultSet.getInt( i++ ) );
        sale.setImmobileId( resultSet.getInt( i++ ) );
        sale.setOwnerId( resultSet.getInt( i++ ) );
        sale.setBuyerId( resultSet.getInt( i++ ) );
        sale.setValue( resultSet.getDouble( i++ ) );
        sale.setPlots( resultSet.getInt( i++ ) );
        sale.setProfit( resultSet.getInt( i++ ) );
        sale.setDescription( resultSet.getString( i++ ) );
        sale.setDate( resultSet.getDate( i++ ) );
        
        return sale;
    }
}