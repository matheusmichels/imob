package model.com.imob.model.data;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matheus Michels
 */
public class Purpose
{
    private String purpose;
    private int id;

    public static final int ALL  = 0;
    public static final int SELL = 1;
    public static final int RENT = 2;
    
    private Purpose( String purpose, int id )
    {
        this.purpose = purpose;
        this.id = id;
    }
    
    public static List<Purpose> getAll()
    {
        return Arrays.asList( new Purpose[]
        {
            new Purpose( "Vender/Alugar" , 0 ),
            new Purpose( "Vender"        , 1 ),
            new Purpose( "Alugar"        , 2 )
        } );
    }
    
    public int getId()
    {
        return id;
    }
    
    public String toString()
    {
        return purpose;
    }
}
