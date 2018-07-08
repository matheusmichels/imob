package view.com.imob.view.panes;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import model.com.imob.model.data.User;
import view.com.imob.view.editors.user.UserEditor;
import view.com.imob.view.panes.ActionPane.Action;
import view.com.imob.view.panes.ActionPane.ActionCategory;
import view.com.imob.view.tables.DefaultTable;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.parts.SearchField;
import view.com.imob.view.tables.UserTable;
import view.com.imob.view.util.EditorCallback;

/**
 *
 * @author matheus.michels
 */
public class UserPane
    extends
        DefaultPane
{
    private static final int FILTER_ALL   = 0;
    private static final int FILTER_NAME  = 1;
    private static final int FILTER_LOGIN = 2;
    private static final int FILTER_EMAIL = 3;
    
    public UserPane()
    {
        initComponents();
    }

    @Override
    public void resize(int height, int width)
    {
        
    }
    
    public void refreshContent()
    {
        try
        {
            List<User> users = model.com.imob.model.ModuleContext.getInstance()
                                                                 .getUserManager()
                                                                 .getValues();
            
            String text = searchField.getText().trim().toLowerCase();
            
            if ( text != null && !text.isEmpty() )
            {
                List<User> filtered = new ArrayList();
                int index = searchField.getIndex();
                
                for ( User u : users )
                {
                    boolean added = false;
                    
                    if ( index == FILTER_EMAIL || index == FILTER_ALL )
                    {
                        if ( !added && u.getEmail().toLowerCase().contains( text ) )
                        {
                            filtered.add( u );
                            added = true;
                        }
                    }
                    
                    if ( index == FILTER_LOGIN || index == FILTER_ALL )
                    {
                        if ( !added && u.getLogin().toLowerCase().contains( text ) )
                        {
                            filtered.add( u );
                            added = true;
                        }
                    }
                    
                    if ( index == FILTER_NAME || index == FILTER_ALL )
                    {
                        if ( !added && u.getName().toLowerCase().contains( text ) )
                        {
                            filtered.add( u );
                            added = true;
                        }
                    }
                }
                
                userTable.setItems( filtered );
            }
            
            else
            {
                userTable.setItems( users );
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void addUser()
    {
        new UserEditor( new EditorCallback( new User() )
        {
            @Override
            public void onEvent()
            {
                try
                {
                    model.com.imob.model.ModuleContext.getInstance().getUserManager().addValue( (User) getSource() );
                    refreshContent();
                }

                catch ( Exception e )
                {
                    handleException( e );
                }
            }
        } ).open();
    }
    
    private void editUser()
    {
        if ( userTable.getSelectedItem() != null )
        {
            new UserEditor( new EditorCallback( userTable.getSelectedItem() )
            {
                @Override
                public void onEvent()
                {
                    try
                    {
                        model.com.imob.model.ModuleContext.getInstance().getUserManager().updateValue( (User) getSource() );
                        refreshContent();
                    }

                    catch ( Exception e )
                    {
                        handleException( e );
                    }
                }
            } ).open();
        }
        
        else
        {
            Prompt.alert( "Selecione um usuário para editar!" );
        }
    }
    
    private void deleteUser()
    {
        if ( userTable.getSelectedItem() != null )
        {
            if ( Prompt.confirm( "Confirmação", "Você tem certeza que deseja excluir esse usuário?" ) )
            {
                try
                {
                    model.com.imob.model.ModuleContext.getInstance().getUserManager().deleteValue( userTable.getSelectedItem() );
                    refreshContent();
                }

                catch ( Exception e )
                {
                    handleException( e );
                }
            }
        }
        
        else
        {
            Prompt.alert( "Selecione um usuário para excluir!" );
        }
    }
    
    public void initComponents()
    {
        setPadding( new Insets( 10, 15, 10, 15 ) );
        
        searchField.setPadding( new Insets( 0, 0, 10, 0 ) );
        searchField.setOptions( new String[]
        {
            "Todos",
            "Nome",
            "Login",
            "Email"
        } );
        
        userTable.setPrefHeight( 1500 );
        
        setTop( searchField );
        setCenter( userTable );
        
        refreshContent();
        
        searchField.addEventHandler( SearchField.ON_SEARCH, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                refreshContent();
            }
        } );
        
        userTable.addEventHandler( DefaultTable.ON_DOUBLE_CLICK, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                editUser();
            }
        } );
    }

    @Override
    public List<ActionCategory> getActionCategories()
    {
        List<ActionCategory> aacs = new ArrayList();
        aacs.add( appActions );
        aacs.add( userActions );
        
        return aacs;
    }
    
    private SearchField searchField = new SearchField();
    private UserTable userTable = new UserTable();
    
    private Action refresh = new Action( "refresh.png", "Atualizar", "Atualizar Tela" )
    {
        @Override
        public void onEvent()
        {
            refreshContent();
        }
    };
    
    private Action addUser = new Action( "add.png", "Adicionar", "Adicionar Usuário" )
    {
        @Override
        public void onEvent()
        {
            addUser();
        }
    };
    
    private Action editUser = new Action( "edit.png", "Editar", "Editar Usuário" )
    {
        @Override
        public void onEvent()
        {
            editUser();
        }
    };
    
    private Action deleteUser = new Action( "delete.png", "Excluir", "Excluir Usuário" )
    {
        @Override
        public void onEvent()
        {
            deleteUser();
        }
    };
    
    private ActionCategory appActions = new ActionCategory( "Aplicação", refresh );
    
    private ActionCategory userActions = new ActionCategory( "Usuário", new Action[]
    {
        addUser,
        editUser,
        deleteUser
    } );
}