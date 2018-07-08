/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.tables;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Payment;
import model.com.imob.model.data.Sale;
import model.com.imob.model.db.service.FinanceManagerService;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class SaleTable
    extends
        DefaultTable<Sale>
{
    public SaleTable()
    {
        setColumns
        (
            new DefaultTable.Column( "#", "id", 80d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
            {
                @Override
                public TableCell<Immobile, Integer> call( TableColumn<Immobile, Integer> param )
                {
                    return new TableCell<Immobile, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            if( ! empty )
                            {
                                try
                                {
                                    FinanceManagerService fm = model.com.imob.model.ModuleContext.getInstance().getFinanceManager();

                                    List<Payment> payments = fm.getRemainingPayments( item, true );

                                    ImageView image = new ImageView( ResourceLocator.getImage( payments.isEmpty() ? "paid.png" : "nopaid.png" ) );
                                    image.setFitWidth( 20 );
                                    image.setFitHeight( 20 );
                                    
                                    setGraphic(image);
                                    setText( String.valueOf( item ) );
                                }
                                
                                catch ( Exception e )
                                {
                                    Prompt.exception(e);
                                }
                            }
                            
                            else
                            {
                                setText( null );
                                setGraphic( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Vendedor", "ownerId", 300d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
            {
                @Override
                public TableCell<Immobile, Integer> call( TableColumn<Immobile, Integer> param )
                {
                    return new TableCell<Immobile, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            if( ! empty )
                            {
                                try
                                {
                                    Client owner = model.com.imob.model.ModuleContext.getInstance().getClientManager().getValue( item );
                                    
                                    setText( owner != null ? owner.getName() : "n/d" );
                                }
                                
                                catch ( Exception e )
                                {
                                    Prompt.exception(e);
                                }
                            }
                            
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Comprador", "buyerId", 300d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
            {
                @Override
                public TableCell<Immobile, Integer> call( TableColumn<Immobile, Integer> param )
                {
                    return new TableCell<Immobile, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            if( ! empty )
                            {
                                try
                                {
                                    Client owner = model.com.imob.model.ModuleContext.getInstance().getClientManager().getValue( item );
                                    
                                    setText( owner != null ? owner.getName() : "n/d" );
                                }
                                
                                catch ( Exception e )
                                {
                                    Prompt.exception(e);
                                }
                            }
                            
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Valor", "value", 150d, new Callback< TableColumn<Immobile, Double>, TableCell<Immobile, Double> >()
            {
                @Override
                public TableCell<Immobile, Double> call( TableColumn<Immobile, Double> param )
                {
                    return new TableCell<Immobile, Double>() 
                    {
                        @Override
                        protected void updateItem( Double item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            if( ! empty )
                            {
                                setText( NumberFormat.getCurrencyInstance().format( item ) );
                            }
                            
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Restante", "id", 150d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
            {
                @Override
                public TableCell<Immobile, Integer> call( TableColumn<Immobile, Integer> param )
                {
                    return new TableCell<Immobile, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            try
                            {
                                if( ! empty )
                                {
                                    Double remaining = model.com.imob.model.ModuleContext.getInstance().getFinanceManager().countRemainingValue( item );
                                    setText( NumberFormat.getCurrencyInstance().format( remaining ) );
                                }

                                else
                                {
                                    setText( null );
                                }
                            }
                            
                            catch ( Exception e )
                            {
                                Prompt.exception( e );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Parcelas", "plots", 150d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
            {
                @Override
                public TableCell<Immobile, Integer> call( TableColumn<Immobile, Integer> param )
                {
                    return new TableCell<Immobile, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            try
                            {
                                if( ! empty )
                                {
                                    setText( item + "x" );
                                }

                                else
                                {
                                    setText( null );
                                }
                            }
                            
                            catch ( Exception e )
                            {
                                Prompt.exception( e );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Ganho Imóvel", "profit", 150d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
            {
                @Override
                public TableCell<Immobile, Integer> call( TableColumn<Immobile, Integer> param )
                {
                    return new TableCell<Immobile, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            try
                            {
                                if( ! empty )
                                {
                                    setText( item + "%" );
                                }

                                else
                                {
                                    setText( null );
                                }
                            }
                            
                            catch ( Exception e )
                            {
                                Prompt.exception( e );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Data Venda", "date", 150d, new Callback< TableColumn<Immobile, Date>, TableCell<Immobile, Date> >()
            {
                @Override
                public TableCell<Immobile, Date> call( TableColumn<Immobile, Date> param )
                {
                    return new TableCell<Immobile, Date>() 
                    {
                        @Override
                        protected void updateItem( Date item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            if( ! empty )
                            {
                                setText( item != null ? new SimpleDateFormat( "dd/MM/yyyy" ).format( item ) : "n/d" );
                            }
                            
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } )
        );
    }
}
