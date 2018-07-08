package model.com.imob.model.data;

/**
 *
 * @author matheus.michels
 */
public class User
    extends
        Identity
{
    public static final int STATUS_DELETED = 0;
    public static final int STATUS_ACTIVE  = 1;
    
    private String name;
    private String login;
    private String password;
    private String email;
    private int status;

    public User()
    {
    }
    
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = status;
    }
}
