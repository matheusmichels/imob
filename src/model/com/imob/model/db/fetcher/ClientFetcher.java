package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.Client;

/**
 *
 * @author matheus.michels
 */
public class ClientFetcher
    implements
        Fetcher<Client>
{
    @Override
    public Client fetch( ResultSet resultSet ) throws Exception 
    {
        int i = 1;
        
        Client client = new Client();
        
        client.setId( resultSet.getInt( i++ ) );
        client.setName( resultSet.getString( i++ ) );
        client.setNumber( resultSet.getInt( i++ ) );
        client.setComplement( resultSet.getString( i++ ) );
        client.setStreet( resultSet.getString( i++ ) );
        client.setDistrict( resultSet.getString( i++ ) );
        client.setCityId( resultSet.getInt( i++ ) );
        client.setCep( resultSet.getString( i++ ) );
        client.setEmail( resultSet.getString( i++ ) );
        client.setRg( resultSet.getString( i++ ) );
        client.setCpf( resultSet.getString( i++ ) );
        client.setCnpj( resultSet.getString( i++ ) );
        client.setPhone( resultSet.getString( i++ ) );
        client.setCellular( resultSet.getString( i++ ) );
        client.setType( resultSet.getInt( i++ ) );
        client.setStatus( resultSet.getInt( i++ ) );
        
        return client;
    }
}
