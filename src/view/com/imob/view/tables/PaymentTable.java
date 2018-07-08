package view.com.imob.view.tables;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Payment;
import model.com.imob.model.db.service.FinanceManagerService;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class PaymentTable
    extends
        DefaultTable<Payment>
{
    public PaymentTable()
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

                                    Payment payment = fm.getPayment( item );

                                    ImageView image = new ImageView( ResourceLocator.getImage( payment.getDate() != null ? "money.png" : "delete.png" ) );
                                    image.setFitWidth( 20);
                                    image.setFitHeight( 20 );
                                    
                                    setGraphic(image);
                                    setText( String.valueOf( payment.getSequence() ) );
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
                                try
                                {
                                    setText( NumberFormat.getCurrencyInstance().format( item ) );
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
            new DefaultTable.Column( "Data Pagamento", "date", 300d, new Callback< TableColumn<Immobile, Date>, TableCell<Immobile, Date> >()
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
