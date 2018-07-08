/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.com.imob.model.data;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matheus Michels
 */
public class ClientType {
    private String type;
    private int id;

    public static final int ALL  = 0;
    public static final int SELL = 1;
    public static final int RENT = 2;
    
    private ClientType( String type, int id )
    {
        this.type = type;
        this.id = id;
    }
    
    public static List<ClientType> getAll()
    {
        return Arrays.asList( new ClientType[]
        {
            new ClientType( "Proprietário/Comprador" , 0 ),
            new ClientType( "Proprietário"           , 1 ),
            new ClientType( "Comprador"              , 2 )
        } );
    }
    
    public int getId()
    {
        return id;
    }
    
    public String toString()
    {
        return type;
    }
}