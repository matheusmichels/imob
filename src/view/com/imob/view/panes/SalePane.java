/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.panes;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import model.com.imob.model.data.Payment;
import model.com.imob.model.data.Sale;
import model.com.imob.model.db.service.FinanceManagerService;
import view.com.imob.view.panes.ActionPane.Action;
import view.com.imob.view.panes.ActionPane.ActionCategory;
import view.com.imob.view.parts.LegendPane;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.tables.DefaultTable;
import view.com.imob.view.tables.PaymentTable;
import view.com.imob.view.tables.SaleTable;

/**
 *
 * @author Matheus Michels
 */
public class SalePane
    extends
        DefaultPane
{
    private boolean isOpen = false;
    private Sale selected = null;
    
    public SalePane()
    {
        initComponents();
    }

    @Override
    public void refreshContent()
    {
        try
        {
            FinanceManagerService fm = model.com.imob.model.ModuleContext.getInstance().getFinanceManager();

            if ( !isOpen )
            {
                List<Sale> sales = fm.getSales();
                saleTable.setItems( sales );
            }
            
            else
            {
                if ( selected != null )
                {
                    List<Payment> payments = fm.getPayments( selected.getId(), true );
                    paymentTable.setItems( payments );
                }
            }
            
            updateActions();
        }
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void enter()
    {
        try
        {
            selected = saleTable.getSelectedItem();

            if ( selected != null )
            {
                isOpen = true;
                
                setCenter( paymentTable );
                setBottom( paymentLegendPane );
                
                refreshContent();
            }

            else
            {
                Prompt.info( "Selecione uma venda para pagar" );
            }
            
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void exit()
    {
        isOpen = false;
        
        setCenter( saleTable );
        setBottom( saleLegendPane );
        
        refreshContent();
    }

    @Override
    public void resize(int height, int width)
    {
        //do nothing
    }
    
    @Override
    public List<ActionPane.ActionCategory> getActionCategories()
    {
        List<ActionCategory> aacs = new ArrayList();
        aacs.add( applicationActions );
        aacs.add( saleActions );
        
        return aacs;
    }
    
    private void updateActions()
    {
        open.setDisable( isOpen );
        exit.setDisable( !isOpen );
        pay.setDisable( !isOpen );
        
        fireActions();
    }
    
    private void pay()
    {
        Payment selected = paymentTable.getSelectedItem();
        
        if ( selected != null )
        {
            if ( selected.getDate() == null )
            {
                try
                {
                    FinanceManagerService fm = model.com.imob.model.ModuleContext.getInstance().getFinanceManager();
                    
                    int minimum = fm.getMinimumPlot( this.selected.getId(), true );
                    
                    if ( minimum == selected.getId())
                    {
                        if ( Prompt.confirm( "Confirmação", "Você tem certeza deseja pagar essa parcela?" ) )
                        {
                            model.com.imob.model.ModuleContext.getInstance().getFinanceManager().payPlot( selected );
                            refreshContent();
                        }
                    }
                    
                    else
                    {
                        Prompt.alert( "Há parcelas anteriores que não foram pagas!" );
                    }
                }

                catch ( Exception e )
                {
                    handleException( e );
                }
            }
            
            else
            {
                Prompt.alert( "Essa parcela já foi paga!" );
            }
        }
        else
        {
            Prompt.info( "Selecione uma parcela para efetuar o pagamento!" );
        }
    }

    @Override
    public void initComponents()
    {
        setPadding( new Insets( 10, 15, 10, 15 ) );
        
        saleTable.setPrefHeight( 1500 );
        
        setCenter( saleTable );
        setBottom( saleLegendPane );
        
        refreshContent();
        
        saleTable.addEventHandler( DefaultTable.ON_DOUBLE_CLICK, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                enter();
            }
        } );
    }
    
    private SaleTable saleTable = new SaleTable();
    private PaymentTable paymentTable = new PaymentTable();
    private LegendPane saleLegendPane = new LegendPane( LegendPane.TYPE_SALE );
    private LegendPane paymentLegendPane = new LegendPane( LegendPane.TYPE_PAYMENT );
    
    private Action refresh = new Action( "refresh.png", "Atualizar", "Atualizar listagem" )
    {
        @Override
        public void onEvent()
        {
            refreshContent();
        }
    };
    
    private Action open = new Action( "open.png", "Entrar", "Abrir lista de pagamentos" )
    {
        @Override
        public void onEvent()
        {
            enter();
        }
    };
    
    private Action exit = new Action( "exit.png", "Voltar", "Voltar" )
    {
        @Override
        public void onEvent()
        {
            exit();
        }
    };
    
    private Action pay = new Action( "pay.png", "Pagar", "Efetuar pagamento" )
    {
        @Override
        public void onEvent()
        {
            pay();
        }
    };
    
    private ActionCategory applicationActions = new ActionCategory( "Aplicação", new Action[]
    {
        refresh,
        open,
        exit
    });
    
    private ActionCategory saleActions = new ActionCategory( "Vendas", new Action[]
    {
       pay 
    });
}