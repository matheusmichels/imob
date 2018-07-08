/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.Immobile;

/**
 *
 * @author matheus.michels
 */
public class ImmobileFetcher
    implements
        Fetcher<Immobile>
{
    @Override
    public Immobile fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 1;
        
        Immobile immobile = new Immobile();
        
        immobile.setId( resultSet.getInt( i++ ) );
        immobile.setNumber( resultSet.getInt( i++ ) );
        immobile.setComplement( resultSet.getString( i++ ) );
        immobile.setStreet( resultSet.getString( i++ ) );
        immobile.setDistrict( resultSet.getString( i++ ) );
        immobile.setCityId( resultSet.getInt( i++ ) );
        immobile.setCep( resultSet.getString( i++ ) );
        immobile.setValue( resultSet.getDouble( i++ ) );
        immobile.setRent( resultSet.getDouble( i++ ) );
        immobile.setDescription( resultSet.getString( i++ ) );
        immobile.setPurpose( resultSet.getInt( i++ ) );
        immobile.setOwnerId( resultSet.getInt( i++ ) );
        immobile.setBuyerId( resultSet.getInt( i++ ) );
        immobile.setStatus( resultSet.getInt( i++ ) );
        
        return immobile;
    }
}
