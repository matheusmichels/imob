package model.com.imob.model.data;

/**
 *
 * @author Matheus Michels
 */
public class City
    extends
        Identity
{
    private String name;
    private int stateId;
    private String cep;

    public City()
    {
    }

    public City( String name, String cep, int stateId )
    {
        this.name = name;
        this.cep = cep;
        this.stateId = stateId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    
    public int getStateId()
    {
        return stateId;
    }

    public void setStateId( int stateId )
    {
        this.stateId = stateId;
    }

    public String getCep()
    {
        return cep;
    }

    public void setCep( String cep )
    {
        this.cep = cep;
    }

    public String toString()
    {
        return name;
    }
}