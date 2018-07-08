/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.User;

/**
 *
 * @author matheus.michels
 */
public class UserFetcher
    implements Fetcher<User>
{
    @Override
    public User fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 1;
        
        User user = new User();
        
        user.setId( resultSet.getInt( i++ ) );
        user.setName( resultSet.getString( i++ ) );
        user.setLogin(resultSet.getString( i++ ) );
        user.setPassword( resultSet.getString( i++ ) );
        user.setEmail( resultSet.getString( i++ ) );
        user.setStatus( resultSet.getInt( i++ ) );
        
        return user;
    }
}
