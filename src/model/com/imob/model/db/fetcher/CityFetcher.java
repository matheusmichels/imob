/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.City;

/**
 *
 * @author Matheus Michels
 */
public class CityFetcher
    implements
        Fetcher<City>
{
    @Override
    public City fetch( ResultSet resultSet ) throws Exception
    {
        int i = 1;
        
        City city = new City();
        
        city.setId( resultSet.getInt( i++ ) );
        city.setName( resultSet.getString( i++ ) );
        city.setStateId( resultSet.getInt( i++ ) );
        city.setCep( resultSet.getString( i++ ) );
        
        return city;
    }
}
