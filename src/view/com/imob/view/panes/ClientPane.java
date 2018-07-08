package view.com.imob.view.panes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.editors.client.ClientEditor;
import view.com.imob.view.panes.ActionPane.ActionCategory;
import view.com.imob.view.tables.ClientTable;
import view.com.imob.view.tables.DefaultTable;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.parts.SearchField;
import view.com.imob.view.reports.ClientListReport;
import view.com.imob.view.reports.ClientReport;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.FileUtilities;

/**
 *
 * @author Matheus Michels
 */
public class ClientPane
    extends
        DefaultPane
{
    private static final int FILTER_ALL    = 0;
    private static final int FILTER_NAME   = 1;
    private static final int FILTER_CITY   = 2;
    private static final int FILTER_RG     = 3;
    
    public ClientPane()
    {
        initComponents();
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            List<Client> clients = model.com.imob.model.ModuleContext.getInstance()
                                                                     .getClientManager()
                                                                     .getValues();
            
            String text = searchField.getText().trim().toLowerCase();
            
            if ( text != null && !text.isEmpty() )
            {
                boolean added = false;
                
                List<Client> filtered = new ArrayList();
                int index = searchField.getIndex();
                
                LocalManagerService lm = model.com.imob.model.ModuleContext.getInstance().getLocalManager();
                
                for ( Client c : clients )
                {
                    if ( index == FILTER_NAME || index == FILTER_ALL )
                    {
                        if ( !added && c.getName().toLowerCase().contains( text ) )
                        {
                            filtered.add( c );
                            added = true;
                        }
                    }
                    
                    if ( index == FILTER_CITY || index == FILTER_ALL )
                    {
                        City city = lm.getCity( c.getCityId() );
                        
                        if ( !added && city.getName().toLowerCase().contains( text ) )
                        {
                            filtered.add( c );
                            added = true;
                        }
                    }
                    
                    if ( index == FILTER_RG || index == FILTER_ALL )
                    {
                        if ( !added && c.getRg().toLowerCase().contains( text ) )
                        {
                            filtered.add( c );
                            added = true;
                        }
                    }
                }
                
                clientTable.setItems( filtered );
            }
            
            else
            {
                clientTable.setItems( clients );
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void addClient()
    {
        new ClientEditor( new EditorCallback( new Client() )
        {
            @Override
            public void onEvent()
            {
                try
                {
                    model.com.imob.model.ModuleContext.getInstance().getClientManager().addValue( (Client) getSource() );
                    refreshContent();
                }

                catch ( Exception e )
                {
                    handleException( e );
                }
            }
        } ).open();
    }
    
    private void editClient()
    {
        if ( clientTable.getSelectedItem() != null )
        {
            new ClientEditor( new EditorCallback( clientTable.getSelectedItem() )
            {
                @Override
                public void onEvent()
                {
                    try
                    {
                        model.com.imob.model.ModuleContext.getInstance().getClientManager().updateValue( (Client) getSource() );
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
            Prompt.alert( "Selecione um cliente para editar!" );
        }
    }
    
    private void deleteClient()
    {
        if ( clientTable.getSelectedItem() != null )
        {
            if ( Prompt.confirm( "Confirmação", "Você tem certeza que deseja excluir esse imóvel?" ) )
            {
                try
                {
                    model.com.imob.model.ModuleContext.getInstance().getClientManager().deleteValue( clientTable.getSelectedItem() );
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
            Prompt.alert( "Selecione um cliente para excluir!" );
        }
    }
    
    private void printClient( boolean list )
    {
        try
        {
            if ( !list )
            {
                Client selected = clientTable.getSelectedItem();

                if ( selected != null )
                {
                    File file = FileUtilities.saveFile( "Imprimir Relatório", "ImmobileReport-" + System.currentTimeMillis() +".pdf" );

                    if ( file != null )
                    {
                        ClientReport report = new ClientReport( selected );
                        report.generatePDF( file );
                    }
                }

                else
                {
                    Prompt.info( "Selecione um imóvel para imprimir" );
                }
            }
            
            else
            {
                File file = FileUtilities.saveFile( "Imprimir Relatório", "ImmobileListReport-" + System.currentTimeMillis() +".pdf" );

                if ( file != null )
                {
                    ClientListReport report = new ClientListReport( model.com.imob.model.ModuleContext.getInstance().getClientManager().getValues() );
                    report.generatePDF( file );
                }
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }

    @Override
    public void resize( int height, int width )
    {
    }
    
    @Override
    public void initComponents()
    {
        setPadding( new Insets( 10, 15, 10, 15 ) );
        
        searchField.setPadding( new Insets( 0, 0, 10, 0 ) );
        searchField.setOptions( new String[]
        {
            "Todos",
            "Nome",
            "Cidade",
            "RG"
        } );
        
        clientTable.setPrefHeight( 1500 );
        
        setTop( searchField );
        setCenter( clientTable );
        
        refreshContent();
        
        searchField.addEventHandler( SearchField.ON_SEARCH, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                refreshContent();
            }
        } );
        
        clientTable.addEventHandler( DefaultTable.ON_SELECT, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                updateActions();
            }
        } );
        
        clientTable.addEventHandler( DefaultTable.ON_DOUBLE_CLICK, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                editClient();
            }
        } );
    }
    
    private void updateActions()
    {
    }

    public List<ActionCategory> getActionCategories()
    {
        return Arrays.asList( new ActionCategory[]
        {
            applicationActions,
            immobileActions,
            reportActions
        });
    }
    
    private SearchField searchField = new SearchField();
    private ClientTable clientTable = new ClientTable();
    
    private ActionPane.Action refresh = new ActionPane.Action( "refresh.png", "Atualizar", "Atualizar listagem de Clientes" )
    {
        @Override
        public void onEvent()
        {
            refreshContent();
        }
    };
    
    private ActionPane.Action addClient = new ActionPane.Action( "add.png", "Adicionar", "Adicionar Cliente" )
    {
        @Override
        public void onEvent()
        {
            addClient();
        }
    };
    
    private ActionPane.Action editClient = new ActionPane.Action( "edit.png", "Editar", "Editar Cliente" )
    {
        @Override
        public void onEvent()
        {
            editClient();
        }
    };
    
    private ActionPane.Action deleteClient = new ActionPane.Action( "delete.png", "Excluir", "Excluir Cliente" )
    {
        @Override
        public void onEvent()
        {
            deleteClient();
        }
    };

    private ActionPane.Action printImmobile = new ActionPane.Action( "print.png", "Resumo", "Imprimir detalhes sobre o Imóvel" )
    {
        @Override
        public void onEvent()
        {
            printClient( false );
        }
    };
    
    private ActionPane.Action printImmobiles = new ActionPane.Action( "list.png", "Lista", "Imprimir listagem de Imóveis" )
    {
        @Override
        public void onEvent()
        {
            printClient( true );
        }
    };
    
    private ActionCategory applicationActions = new ActionCategory( "Aplicação", refresh );
    private ActionCategory immobileActions = new ActionCategory( "Cliente", new ActionPane.Action[]
    {
        addClient,
        editClient,
        deleteClient
    });
    private ActionCategory reportActions = new ActionCategory( "Relatório", new ActionPane.Action[]
    {
        printImmobile,
        printImmobiles
    });
}