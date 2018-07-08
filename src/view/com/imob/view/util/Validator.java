package view.com.imob.view.util;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

/**
 *
 * @author Matheus Michels
 */
public class Validator
{
    private static final int[] CPF_WEIGHT = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
    private static final int[] CNPJ_WEIGHT = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
    
    public static boolean cep( String cep )
    {
        return false;
    }
    
    public static boolean cnpj( String cnpj )
    {
        if ( cnpj == null || cnpj.length() != 14 )
        {
            return false;
        }

        Integer digit1 = calculateDigit( cnpj.substring( 0,12 ), CNPJ_WEIGHT );
        Integer digit2 = calculateDigit( cnpj.substring( 0,12 ) + digit1, CNPJ_WEIGHT );
    
        return cnpj.equals( cnpj.substring( 0,12 ) + digit1.toString() + digit2.toString() );
    }
    
    public static boolean cpf( String cpf )
    {
        if ( cpf == null || cpf.length() != 11 )
        {
            return false;
        }

        Integer digit1 = calculateDigit( cpf.substring(0,9), CPF_WEIGHT );
        Integer digit2 = calculateDigit( cpf.substring(0,9) + digit1, CPF_WEIGHT );
    
        return cpf.equals( cpf.substring(0,9) + digit1.toString() + digit2.toString() );
    }
    
    public static boolean date( String date )
    {
        if ( date != null )
        {
            if ( DateValidator.getInstance().isValid( date, "dd/MM/yyyy" ) )
            {
                return true;
            }
            
            return false;
        }

        return true;
    }
    
    public static boolean email( String email )
    {
        if ( email != null )
        {
            if ( EmailValidator.getInstance().isValid( email ) )
            {
                return true;
            }
            
            return false;
        }
        
        return false;
    }
    
    public static boolean phone( String phone )
    {
        return false;
    }
    
    public static boolean integer( String integer )
    {
        if ( integer != null )
        {
            if ( IntegerValidator.getInstance().isValid( integer ) )
            {
                return true;
            }
            
            return false;
        }
        
        return false;
    }
            
    private static int calculateDigit( String s, int[] weight )
    {
        int sum = 0;
      
        for ( int i = s.length() - 1, digit; i >= 0; i-- )
        {
           digit = Integer.parseInt( s.substring( i, i + 1 ) );
           sum += digit * weight[ weight.length - s.length() + i ];
        }

        sum = 11 - sum % 11;
        
        return sum > 9 ? 0 : sum;
    }
}