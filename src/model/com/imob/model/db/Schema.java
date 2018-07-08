package model.com.imob.model.db;

import model.com.imob.model.db.fetcher.CityFetcher;
import model.com.imob.model.db.fetcher.ClientFetcher;
import model.com.imob.model.db.fetcher.ImmobileFetcher;
import model.com.imob.model.db.fetcher.PaymentFetcher;
import model.com.imob.model.db.fetcher.RentFetcher;
import model.com.imob.model.db.fetcher.SaleFetcher;
import model.com.imob.model.db.fetcher.StateFetcher;
import model.com.imob.model.db.fetcher.UserFetcher;

/**
 *
 * @author matheus.michels
 */
public class Schema
{
    public static class Users
    {
        public String name = "users";
        
        public static final Users table = new Users( null );
        public final Columns columns;
        
        public final UserFetcher fetcher = new UserFetcher();
        
        public final String select;
        
        private Users( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String NAME;
            public String LOGIN;
            public String PASSWORD;
            public String EMAIL;
            public String STATUS;
            
            public Columns( String alias )
            {
                ID        = alias + "id";
                NAME      = alias + "name";
                LOGIN     = alias + "login";
                PASSWORD  = alias + "password";
                EMAIL     = alias + "email";
                STATUS    = alias + "status";
            }
            
            @Override
            public String toString() 
            {
                return ID        + ", " +
                       NAME      + ", " +
                       LOGIN     + ", " +
                       PASSWORD  + ", " +
                       EMAIL     + ", " +
                       STATUS;
            }
        }
    }
    
    public static class Clients
    {
        public String name = "clients";
        
        public static final Clients table = new Clients( null );
        public final Columns columns;
        
        public final ClientFetcher fetcher = new ClientFetcher();
        
        public final String select;
        
        private Clients( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String NAME;
            public String NUMBER;
            public String COMPLEMENT;
            public String STREET;
            public String DISTRICT;
            public String REF_CITY;
            public String CEP;
            public String EMAIL;
            public String RG;
            public String CPF;
            public String CNPJ;
            public String PHONE;
            public String CELLULAR;
            public String TYPE;
            public String STATUS;
            
            public Columns( String alias )
            {
                ID          = alias + "id";
                NAME        = alias + "name";
                NUMBER      = alias + "number";  
                COMPLEMENT  = alias + "complement";
                STREET      = alias + "street";
                DISTRICT    = alias + "district";
                REF_CITY    = alias + "ref_city";
                CEP         = alias + "cep";
                EMAIL       = alias + "email";
                RG          = alias + "rg";
                CPF         = alias + "cpf";
                CNPJ        = alias + "cnpj";
                PHONE       = alias + "phone";
                CELLULAR    = alias + "cellular";
                TYPE        = alias + "type";
                STATUS      = alias + "status";
            }
            
            @Override
            public String toString() 
            {
                return ID   + ", " +
                NAME        + ", " +
                NUMBER      + ", " +
                COMPLEMENT  + ", " +
                STREET      + ", " +
                DISTRICT    + ", " +
                REF_CITY    + ", " +
                CEP         + ", " +
                EMAIL       + ", " +
                RG          + ", " +
                CPF         + ", " +
                CNPJ        + ", " +
                PHONE       + ", " +
                CELLULAR    + ", " +
                TYPE        + ", " +
                STATUS;
            }
        }
    }
    
    public static class Immobiles
    {
        public String name = "immobiles";
        
        public static final Immobiles table = new Immobiles( null );
        public final Columns columns;
        
        public final ImmobileFetcher fetcher = new ImmobileFetcher();
        
        public final String select;
        
        private Immobiles( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }

        public class Columns
        {
            public String ID;
            public String NUMBER;
            public String COMPLEMENT;
            public String STREET;
            public String DISTRICT;
            public String REF_CITY;
            public String CEP;
            public String VALUE;
            public String RENT;
            public String DESCRIPTION;
            public String PURPOSE;
            public String REF_OWNER;
            public String REF_BUYER;
            public String STATUS;
            
            public Columns( String alias )
            {
                ID           = alias + "id";
                NUMBER       = alias + "number";  
                COMPLEMENT   = alias + "complement";
                STREET       = alias + "street";
                DISTRICT     = alias + "district";
                REF_CITY     = alias + "ref_city";
                CEP          = alias + "cep";
                VALUE        = alias + "value";
                RENT         = alias + "rent";
                DESCRIPTION  = alias + "description";
                PURPOSE      = alias + "purpose";
                REF_OWNER    = alias + "ref_owner";
                REF_BUYER    = alias + "ref_buyer";
                STATUS       = alias + "status";
            }
            
            @Override
            public String toString() 
            {
                return ID    + ", " +
                NUMBER       + ", " +
                COMPLEMENT   + ", " +
                STREET       + ", " +
                DISTRICT     + ", " +
                REF_CITY     + ", " +
                CEP          + ", " +
                VALUE        + ", " +
                RENT         + ", " +
                DESCRIPTION  + ", " +
                PURPOSE      + ", " +
                REF_OWNER    + ", " +
                REF_BUYER    + ", " +
                STATUS;
            }
        }
    }
    
    public static class Cities
    {
        public String name = "cities";
        
        public static final Cities table = new Cities( null );
        public final Columns columns;
        
        public final CityFetcher fetcher = new CityFetcher();
        
        public final String select;
        
        private Cities( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String NAME;
            public String REF_STATE;
            public String CEP;
            
            public Columns( String alias )
            {
                ID           = alias + "id";
                NAME         = alias + "name";  
                REF_STATE    = alias + "ref_state";
                CEP          = alias + "cep";
            }
            
            @Override
            public String toString() 
            {
                return ID  + ", " +
                NAME       + ", " +
                REF_STATE  + ", " +
                CEP;
            }
        }
    }
    
    public static class States
    {
        public String name = "states";
        
        public static final States table = new States( null );
        public final Columns columns;
        
        public final StateFetcher fetcher = new StateFetcher();
        
        public final String select;
        
        private States( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String NAME;
            public String UF;
            
            public Columns( String alias )
            {
                ID     = alias + "id";
                NAME   = alias + "name";  
                UF     = alias + "uf";
            }
            
            @Override
            public String toString() 
            {
                return ID  + ", " +
                NAME       + ", " +
                UF;
            }
        }
    }
    
    public static class Sales
    {
        public String name = "sales";
        
        public static final Sales table = new Sales( null );
        public final Columns columns;
        
        public final SaleFetcher fetcher = new SaleFetcher();
        
        public final String select;
        
        private Sales( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String REF_IMMOBILE;
            public String REF_OWNER;
            public String REF_BUYER;
            public String VALUE;
            public String PLOTS;
            public String PROFIT;
            public String DESCRIPTION;
            public String DT_SALE;
            
            public Columns( String alias )
            {
                ID            = alias + "id";
                REF_IMMOBILE  = alias + "ref_immobile";
                REF_OWNER     = alias + "ref_owner";
                REF_BUYER     = alias + "ref_buyer";
                VALUE         = alias + "value";
                PLOTS         = alias + "plots";
                PROFIT        = alias + "profit";
                DESCRIPTION   = alias + "description";
                DT_SALE       = alias + "dt_sale";
            }
            
            @Override
            public String toString() 
            {
                return ID     + ", " +
                REF_IMMOBILE  + ", " +
                REF_OWNER     + ", " +
                REF_BUYER     + ", " +
                VALUE         + ", " +
                PLOTS         + ", " +
                PROFIT        + ", " +
                DESCRIPTION   + ", " +
                DT_SALE;
            }
        }
    }
    
    public static class Rents
    {
        public String name = "rents";
        
        public static final Rents table = new Rents( null );
        public final Columns columns;
        
        public final RentFetcher fetcher = new RentFetcher();
        
        public final String select;
        
        private Rents( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String REF_IMMOBILE;
            public String REF_OWNER;
            public String REF_RENTER;
            public String VALUE;
            public String MONTHS;
            public String PROFIT;
            public String DESCRIPTION;
            public String DT_RENT;
            
            public Columns( String alias )
            {
                ID            = alias + "id";
                REF_IMMOBILE  = alias + "ref_immobile";
                REF_OWNER     = alias + "ref_owner";
                REF_RENTER    = alias + "ref_renter";
                VALUE         = alias + "value";
                MONTHS        = alias + "months";
                PROFIT        = alias + "profit";
                DESCRIPTION   = alias + "description";
                DT_RENT       = alias + "dt_rent";
            }
            
            @Override
            public String toString() 
            {
                return ID     + ", " +
                REF_IMMOBILE  + ", " +
                REF_OWNER     + ", " +
                REF_RENTER    + ", " +
                VALUE         + ", " +
                MONTHS        + ", " +
                PROFIT        + ", " +
                DESCRIPTION   + ", " +
                DT_RENT;
            }
        }
    }
    
    public static class Payments
    {
        public String name = "payments";
        
        public static final Payments table = new Payments( null );
        public final Columns columns;
        
        public final PaymentFetcher fetcher = new PaymentFetcher();
        
        public final String select;
        
        private Payments( String alias ) 
        {
            this.name = alias != null ? name + " " + alias : name;
            
            columns = new Columns( alias != null ? alias + "." : "" );
            
            select = "select " + columns + " from " + this.name;
        }
        
        public class Columns
        {
            public String ID;
            public String SEQUENCE;
            public String REF_SALE;
            public String REF_RENT;
            public String VALUE;
            public String DT_PAYMENT;
            
            public Columns( String alias )
            {
                ID          = alias + "id";
                SEQUENCE    = alias + "sequence";
                REF_SALE    = alias + "ref_sale";
                REF_RENT    = alias + "ref_rent";
                VALUE       = alias + "value";
                DT_PAYMENT  = alias + "dt_payment";
            }
            
            @Override
            public String toString() 
            {
                return ID   + ", " +
                SEQUENCE    + ", " +
                REF_SALE    + ", " +
                REF_RENT    + ", " +
                VALUE       + ", " +
                DT_PAYMENT;
            }
        }
    }
}