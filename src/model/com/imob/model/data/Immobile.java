package model.com.imob.model.data;

/**
 *
 * @author matheus.michels
 */
public class Immobile
    extends
        Identity
{
    public static final int STATUS_DELETED  = 0;
    public static final int STATUS_AVAIABLE = 1;
    public static final int STATUS_RESERVED = 2;
    public static final int STATUS_RENTED   = 3;
    public static final int STATUS_BOUGHT   = 4;
    
    private int number;
    private String complement;
    private String street;
    private String district;
    private int cityId;
    private String cep;
    private double value;
    private double rent;
    private String description;
    private int purpose;
    private int ownerId;
    private int buyerId;
    private int status;

    public Immobile()
    {
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
    
    public double getValue()
    {
        return value;
    }

    public void setValue( double value )
    {
        this.value = value;
    }
    
    public double getRent()
    {
        return rent;
    }

    public void setRent( double rent )
    {
        this.rent = rent;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }
    
    public int getPurpose()
    {
        return purpose;
    }

    public void setPurpose( int purpose )
    {
        this.purpose = purpose;
    }

    public int getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId( int ownerId )
    {
        this.ownerId = ownerId;
    }

    public int getBuyerId()
    {
        return buyerId;
    }

    public void setBuyerId( int buyerId )
    {
        this.buyerId = buyerId;
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
