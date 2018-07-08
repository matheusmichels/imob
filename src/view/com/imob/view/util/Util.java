package view.com.imob.view.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.Normalizer;
import view.com.imob.view.parts.Prompt;

/**
 *
 * @author matheus.michels
 */
public class Util
{
    public static String hash( String text )
    {
        try
        {
            return new BigInteger( 1, MessageDigest.getInstance( "MD5" ).digest( text.getBytes( "UTF-8" ) ) ).toString( 16 );
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
        
        return "(FAIL)";
    }
    
    public static String normalize( String text )
    {
        return Normalizer.normalize( text, Normalizer.Form.NFKD ).replaceAll( "\\p{InCombiningDiacriticalMarks}+", "" );
    }
}