package view.com.imob.view.util;

import javafx.stage.Window;
import model.com.imob.model.data.User;
import view.com.imob.view.parts.Prompt;

/**
 *
 * @author matheus.michels
 */
public class ApplicationUtilities
{
    private static ApplicationUtilities applicationUtilities;
    
    private User currentUser;
    private Window window;
    
    private ApplicationUtilities() {}
    
    public static ApplicationUtilities getInstance()
    {
        if ( applicationUtilities == null )
        {
            applicationUtilities = new ApplicationUtilities();
        }
        
        return applicationUtilities;
    }
     
    public User getCurrentUser()
    {
        return currentUser;
    }
    
    public void setCurrentUser( User currentUser )
    {
        this.currentUser = currentUser;
    }
    
    public String getCompany()
    {
        return "iMob - Gestão Imobiliária";
    }
    
    public Window getWindow()
    {
        return window;
    }
    
    public void setWindow( Window window )
    {
        this.window = window;
    }
    
    public static void handleException( Exception e )
    {
        Prompt.exception( e );
    }
}
