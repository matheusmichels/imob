package model.com.imob.model;

import model.com.imob.model.db.service.LocalManagerService;
import model.com.imob.model.db.service.ClientManagerService;
import model.com.imob.model.db.service.ImmobileManagerService;
import model.com.imob.model.db.service.FinanceManagerService;
import model.com.imob.model.db.service.UserManagerService;

/**
 *
 * @author matheus.michels
 */
public class ModuleContext
{
    private static ModuleContext moduleContext;
    
    public static ModuleContext getInstance()
    {
        if( moduleContext == null )
        {
            moduleContext = new ModuleContext();
        }
        
        return  moduleContext;
    }
    
    public UserManagerService getUserManager()
    {
        return UserManagerService.getInstance();
    }

    public ClientManagerService getClientManager()
    {
        return ClientManagerService.getInstance();
    }
    
    public ImmobileManagerService getImmobileManager()
    {
        return ImmobileManagerService.getInstance();
    }
    
    public FinanceManagerService getFinanceManager()
    {
        return FinanceManagerService.getInstance();
    }
    
    public LocalManagerService getLocalManager()
    {
        return LocalManagerService.getInstance();
    }
}
