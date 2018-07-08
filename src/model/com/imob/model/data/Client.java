package model.com.imob.model.data;

/**
 *
 * @author Matheus Michels
 */
public class Client
    extends
        Identity
{
    public static final int TYPE_ALL     = 0;
    public static final int TYPE_SELLER  = 1;
    public static final int TYPE_BUYER   = 2;
    
    public static final int STATUS_DELETED  = 0;
    public static final int STATUS_ACTIVE   = 1;
    
    private String name;
    private int number;
    private String complement;
    private String street;
    private String district;
    private int cityId;
    private String cep;
    private String email;
    private String rg;
    private String cpf;
    private String cnpj;
    private String phone;
    private String cellular;
    private int type;
    private int status;

    public Client()
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

    public int getNumber()
    {
        return number;
    }

    public void setNumber( int number )
    {
        this.number = number;
    }
    
    public String getComplement()
    {
        return complement;
    }

    public void setComplement( String complement )
    {
        this.complement = complement;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict( String district )
    {
        this.district = district;
    }

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId( int cityId )
    {
        this.cityId = cityId;
    }
    
    public String getCep()
    {
        return cep;
    }

    public void setCep( String cep )
    {
        this.cep = cep;
    }
   
    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getRg()
    {
        return rg;
    }

    public void setRg( String rg )
    {
        this.rg = rg;
    }

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf( String cpf )
    {
        this.cpf = cpf;
    }
    
    public String getCnpj()
    {
        return cnpj;
    }

    public void setCnpj( String cnpj )
    {
        this.cnpj = cnpj;
    }
    
    public String getPhone()
    {
        return phone;
    }

    public void setPhone( String phone )
    {
        this.phone = phone;
    }

    public String getCellular()
    {
        return cellular;
    }

    public void setCellular( String cellular )
    {
        this.cellular = cellular;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = status;
    }
    
    public String toString()
    {
        return name;
    }
}