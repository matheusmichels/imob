/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.Rent;

/**
 *
 * @author Matheus Michels
 */
public class RentFetcher
    implements
        Fetcher<Rent>
{
    @Override
    public Rent fetch( ResultSet resultSet ) throws Exception
    {
        int i = 1;
        
        Rent rent = new Rent();
        
        rent.setId( resultSet.getInt( i++ ) );
        rent.setImmobileId( resultSet.getInt( i++ ) );
        rent.setOwnerId( resultSet.getInt( i++ ) );
        rent.setRenterId( resultSet.getInt( i++ ) );
        rent.setValue( resultSet.getDouble( i++ ) );
        rent.setMonths( resultSet.getInt( i++ ) );
        rent.setProfit( resultSet.getInt( i++ ) );
        rent.setDescription( resultSet.getString( i++ ) );
        rent.setDate( resultSet.getDate( i++ ) );
        
        return rent;
    }
}
