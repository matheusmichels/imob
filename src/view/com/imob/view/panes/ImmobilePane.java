package view.com.imob.view.panes;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Payment;
import model.com.imob.model.data.Purpose;
import model.com.imob.model.data.Rent;
import model.com.imob.model.data.Sale;
import model.com.imob.model.data.State;
import model.com.imob.model.db.service.ClientManagerService;
import model.com.imob.model.db.service.FinanceManagerService;
import model.com.imob.model.db.service.ImmobileManagerService;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.editors.immobile.ImmobileEditor;
import view.com.imob.view.editors.immobile.RentEditor;
import view.com.imob.view.editors.immobile.SaleEditor;
import view.com.imob.view.panes.ActionPane.Action;
import view.com.imob.view.panes.ActionPane.ActionCategory;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.parts.SearchField;
import view.com.imob.view.reports.ImmobileListReport;
import view.com.imob.view.reports.ImmobileReport;
import view.com.imob.view.tables.DefaultTable;
import view.com.imob.view.tables.ImmobileTable;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.FileUtilities;

/**
 *
 * @author matheus.michels
 */
public class ImmobilePane
    extends
        DefaultPane
{
    private static final int FILTER_ALL   = 0;
    private static final int FILTER_CEP   = 1;
    private static final int FILTER_CITY  = 2;
    private static final int FILTER_STATE = 3;
    
    public ImmobilePane()
    {
        initComponents();
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            List<Immobile> immobiles = model.com.imob.model.ModuleContext.getInstance()
                                                                         .getImmobileManager()
                                                                         .getValues();
            
            String text = searchField.getText().trim().toLowerCase();
            
            if ( text != null && !text.isEmpty() )
            {
                boolean added = false;
                
                List<Immobile> filtered = new ArrayList();
                int index = searchField.getIndex();
                
                LocalManagerService lm = model.com.imob.model.ModuleContext.getInstance().getLocalManager();
                
                for ( Immobile i : immobiles )
                {
                    if ( index == FILTER_CEP || index == FILTER_ALL )
                    {
                        if ( !added && i.getCep().toLowerCase().contains( text ) )
                        {
                            filtered.add( i );
                            added = true;
                        }
                    }
                    
                    if ( index == FILTER_CITY || index == FILTER_ALL )
                    {
                        City city = lm.getCity( i.getCityId() );
                        
                        if ( !added && city.getName().toLowerCase().contains( text ) )
                        {
                            filtered.add( i );
                            added = true;
                        }
                    }
                    
                    if ( index == FILTER_STATE || index == FILTER_ALL )
                    {
                        City city = lm.getCity( i.getCityId() );
                        State state = lm.getState( city.getStateId() );

                        if ( !added && state.getName().toLowerCase().contains( text ) )
                        {
                            filtered.add( i );
                            added = true;
                        }
                    }
                }
                
                immobileTable.setItems( filtered );
            }
            
            else
            {
                immobileTable.setItems( immobiles );
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void addImmobile()
    {
        new ImmobileEditor( new EditorCallback( new Immobile() )
        {
            @Override
            public void onEvent()
            {
                try
                {
                    model.com.imob.model.ModuleContext.getInstance().getImmobileManager().addValue( (Immobile) getSource() );
                    refreshContent();
                }

                catch ( Exception e )
                {
                    handleException( e );
                }
            }
        } ).open();
    }
    
    private void editImmobile()
    {
        if ( immobileTable.getSelectedItem() != null )
        {
            new ImmobileEditor( new EditorCallback( immobileTable.getSelectedItem() )
            {
                @Override
                public void onEvent()
                {
                    try
                    {
                        model.com.imob.model.ModuleContext.getInstance().getImmobileManager().updateValue( (Immobile) getSource() );
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
            Prompt.info( "Selecione um imóvel para editar!" );
        }
    }
    
    private void deleteImmobile()
    {
        if ( immobileTable.getSelectedItem() != null )
        {
            if ( Prompt.confirm( "Confirmação", "Você tem certeza que deseja excluir esse imóvel?" ) )
            {
                try
                {
                    model.com.imob.model.ModuleContext.getInstance().getImmobileManager().deleteValue( immobileTable.getSelectedItem() );
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
            Prompt.info( "Selecione um imóvel para excluir!" );
        }
    }
    
    private void printImmobile( boolean list )
    {
        try
        {
            if ( !list )
            {
                Immobile selected = immobileTable.getSelectedItem();

                if ( selected != null )
                {
                    File file = FileUtilities.saveFile( "Imprimir Relatório", "ImmobileReport-" + System.currentTimeMillis() +".pdf" );

                    if ( file != null )
                    {
                        ImmobileReport report = new ImmobileReport( selected );
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
                    ImmobileListReport report = new ImmobileListReport( model.com.imob.model.ModuleContext.getInstance().getImmobileManager().getValues() );
                    report.generatePDF( file );
                }
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void sellImmobile()
    {
        Immobile selected = immobileTable.getSelectedItem();
        
        if ( selected != null )
        {
            Sale sale = new Sale();
            
            sale.setDate( Date.valueOf( LocalDate.now() ) );
            sale.setImmobileId( selected.getId() );
            sale.setOwnerId( selected.getOwnerId() );
            
            new SaleEditor( new EditorCallback<Sale>( sale )
            {
                @Override
                public void onEvent()
                {
                    try
                    {
                        ImmobileManagerService im = model.com.imob.model.ModuleContext
                                                                        .getInstance()
                                                                        .getImmobileManager();
                        ClientManagerService cm = model.com.imob.model.ModuleContext
                                                                      .getInstance()
                                                                      .getClientManager();
                        
                        FinanceManagerService fm = model.com.imob.model.ModuleContext
                                                                       .getInstance()
                                                                       .getFinanceManager();
                        
                        //imovel atualizado
                        Immobile i = im.getValue( selected.getId() );
                        i.setBuyerId( getSource().getBuyerId() );
                        i.setStatus( Immobile.STATUS_BOUGHT );
                        
                        im.updateValue( i );
                        fm.addSale( getSource() );
                        
                        Sale sale = fm.getLastSale();
                        
                        if ( sale != null )
                        {
                            Payment payment = new Payment();
                            payment.setSaleId( sale.getId() );
                            payment.setValue( getSource().getValue() / getSource().getPlots() );
                            
                            fm.addPayments( payment, getSource().getPlots() );
                        }
                        
                        if ( Prompt.confirm( "ATENÇÃO", "Deseja importar as informações do imóvel para a localização do comprador?" ) )
                        {
                            Client c = cm.getValue( getSource().getBuyerId() );
                        
                            if ( c != null )
                            {
                                c.setStreet( i.getStreet() );
                                c.setNumber( i.getNumber() );
                                c.setComplement( i.getComplement() );
                                c.setStreet( i.getStreet() );
                                c.setDistrict( i.getDistrict() );
                                c.setCityId( i.getCityId() );
                                c.setCep( i.getCep() );

                                cm.updateValue( c );
                            }
                            
                            refreshContent();
                        }
                                
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
            Prompt.info( "Selecione um imóvel para vender" );
        }
    }
    
    private void rentImmobile()
    {
        Immobile selected = immobileTable.getSelectedItem();
        
        if ( selected != null )
        {
            Rent rent = new Rent();
            
            rent.setDate( Date.valueOf( LocalDate.now() ) );
            rent.setImmobileId( selected.getId() );
            rent.setOwnerId( selected.getOwnerId() );
            
            new RentEditor( new EditorCallback<Rent>( rent )
            {
                @Override
                public void onEvent()
                {
                    try
                    {
                        ImmobileManagerService im = model.com.imob.model.ModuleContext
                                                                        .getInstance()
                                                                        .getImmobileManager();
                        ClientManagerService cm = model.com.imob.model.ModuleContext
                                                                      .getInstance()
                                                                      .getClientManager();
                        
                        FinanceManagerService fm = model.com.imob.model.ModuleContext
                                                                       .getInstance()
                                                                       .getFinanceManager();
                        
                        //imovel atualizado
                        Immobile i = im.getValue( selected.getId() );
                        i.setBuyerId( getSource().getRenterId() );
                        i.setStatus( Immobile.STATUS_RENTED );
                        
                        im.updateValue( i );
                        fm.addRent( getSource() );
                        
                        Rent rent = fm.getLastRent();
                        
                        if ( rent != null )
                        {
                            Payment payment = new Payment();
                            payment.setRentId( rent.getId() );
                            payment.setValue( getSource().getValue() );
                            
                            fm.addPayments( payment, getSource().getMonths() );
                        }
                        
                        if ( Prompt.confirm( "ATENÇÃO", "Deseja importar as informações do imóvel para a localização do comprador?" ) )
                        {
                            Client c = cm.getValue( getSource().getRenterId() );
                        
                            if ( c != null )
                            {
                                c.setStreet( i.getStreet() );
                                c.setNumber( i.getNumber() );
                                c.setComplement( i.getComplement() );
                                c.setStreet( i.getStreet() );
                                c.setDistrict( i.getDistrict() );
                                c.setCityId( i.getCityId() );
                                c.setCep( i.getCep() );

                                cm.updateValue( c );
                            }
                            
                            refreshContent();
                        }
                                
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
            Prompt.info( "Selecione um imóvel para vender" );
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
            "CEP",
            "Cidade",
            "Estado"
        } );
        
        immobileTable.setPrefHeight( 1500 );
        
        setTop( searchField );
        setCenter( immobileTable );
        
        refreshContent();
        updateActions();
        
        searchField.addEventHandler( SearchField.ON_SEARCH, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                refreshContent();
            }
        } );
        
        immobileTable.addEventHandler( DefaultTable.ON_SELECT, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                updateActions();
            }
        } );
        
        immobileTable.addEventHandler( DefaultTable.ON_DOUBLE_CLICK, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                editImmobile();
            }
        } );
    }
    
    private void updateActions()
    {
        Immobile i = immobileTable.getSelectedItem();
        
        if ( i != null )
        {
            sellImmobile.setDisable( i.getPurpose() == Purpose.RENT || i.getBuyerId() != 0   );
            rentImmobile.setDisable( i.getPurpose() == Purpose.SELL || i.getBuyerId() != 0 );
//            releaseImmobile.setDisable( i.getStatus() == Immobile.STATUS_AVAIABLE );
//            prorrogateImmobile.setDisable( i.getStatus() != Immobile.STATUS_RENTED );
        }
        
        else
        {
            sellImmobile.setDisable( true );
            rentImmobile.setDisable( true );
//            releaseImmobile.setDisable( true );
//            prorrogateImmobile.setDisable( true );
        }
        
        fireActions();
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
    private ImmobileTable immobileTable = new ImmobileTable();
    
    private Action refresh = new Action( "refresh.png", "Atualizar", "Atualizar listagem de Imóveis" )
    {
        @Override
        public void onEvent()
        {
            refreshContent();
        }
    };
    
    private Action addImmobile = new Action( "add.png", "Adicionar", "Adicionar Imóvel" )
    {
        @Override
        public void onEvent()
        {
            addImmobile();
        }
    };
    
    private Action editImmobile = new Action( "edit.png", "Editar", "Editar Imóvel" )
    {
        @Override
        public void onEvent()
        {
            editImmobile();
        }
    };
    
    private Action deleteImmobile = new Action( "delete.png", "Excluir", "Excluir Imóvel" )
    {
        @Override
        public void onEvent()
        {
            deleteImmobile();
        }
    };
    
    private Action sellImmobile = new Action( "sell.png", "Vender", "Vender Imóvel" )
    {
        @Override
        public void onEvent()
        {
            sellImmobile();
        }
    };
    
    private Action rentImmobile = new Action( "rent.png", "Alugar", "Alugar Imóvel" )
    {
        @Override
        public void onEvent()
        {
            rentImmobile();
        }
    };
    
//    private Action releaseImmobile = new Action( "release.png", "Liberar", "Liberar Imóvel" )
//    {
//        @Override
//        public void onEvent()
//        {
//            
//        }
//    };
//    
//    private Action prorrogateImmobile = new Action( "timer.png", "Prorrogar", "Prorrogar aluguel do Imóvel" )
//    {
//        @Override
//        public void onEvent()
//        {
//            
//        }
//    };
    
    private Action printImmobile = new Action( "print.png", "Resumo", "Imprimir detalhes sobre o Imóvel" )
    {
        @Override
        public void onEvent()
        {
            printImmobile( false );
        }
    };
    
    private Action printImmobiles = new Action( "list.png", "Lista", "Imprimir listagem de Imóveis" )
    {
        @Override
        public void onEvent()
        {
            printImmobile( true );
        }
    };
    
    private ActionCategory applicationActions = new ActionCategory( "Aplicação", refresh );
    private ActionCategory immobileActions = new ActionCategory( "Imóvel", new Action[]
    {
        addImmobile,
        editImmobile,
        deleteImmobile,
        sellImmobile,
        rentImmobile
//        releaseImmobile,
//        prorrogateImmobile
    });
    private ActionCategory reportActions = new ActionCategory( "Relatório", new Action[]
    {
        printImmobile,
        printImmobiles
    });
}