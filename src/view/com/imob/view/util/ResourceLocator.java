package view.com.imob.view.util;

/**
 *
 * @author matheus.michels
 */
public class ResourceLocator
{
    public static String getImage( String src )
    {
        return "file:src/res/" + src;
    }
    
    public static String getStylesheet( String src )
    {
        return "file:src/css/" + src;
    }
    
    public static String getJSON()
    {
        return "file:src/cities/cities.json";
    }
}
