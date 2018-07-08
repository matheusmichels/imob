package model.com.imob.model.data;

/**
 *
 * @author Matheus Michels
 */
public class State
    extends
        Identity
{
    private String name;
    private String uf;

    public State()
    {
    }

    public State( String name, String uf )
    {
        this.name = name;
        this.uf = uf;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getUf()
    {
        return uf;
    }

    public void setUf( String uf )
    {
        this.uf = uf;
    }
    
    @Override
    public String toString()
    {
        return uf;
    }
}