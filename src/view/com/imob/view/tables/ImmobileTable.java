package view.com.imob.view.tables;

import java.text.NumberFormat;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Purpose;
import model.com.imob.model.data.User;
import view.com.imob.view.parts.Prompt;

/**
 *
 * @author matheus.michels
 */
public class ImmobileTable
    extends
        DefaultTable<Immobile>
{
    public ImmobileTable()
    {
        setColumns
        (
            new DefaultTable.Column( "#", "id", 30d ),
            new DefaultTable.Column( "Status", "status", 100d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
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
                                String status = item == Immobile.STATUS_AVAIABLE ? "Disponível" :
                                                item == Immobile.STATUS_RESERVED ? "Reservado"  :
                                                item == Immobile.STATUS_RENTED   ? "Alugado"    :
                                                item == Immobile.STATUS_BOUGHT   ? "Vendido" : "";

                                setText( status );
                            }
                            
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Número", "number", 85d ),
            new DefaultTable.Column( "Rua", "street", 250d ),
            new DefaultTable.Column( "Bairro", "district", 210d ),
            new DefaultTable.Column( "Cidade", "cityId", 210d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
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
                                    City city = model.com.imob.model.ModuleContext.getInstance()
                                                                                  .getLocalManager()
                                                                                  .getCity( item );

                                    if( city != null )
                                    {
                                        setText( city.getName() );
                                    }
                                }

                                catch ( Exception e )
                                {
                                    Prompt.exception( e );
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
            new DefaultTable.Column( "CEP", "cep", 110d ),
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
            new DefaultTable.Column( "Aluguel", "rent", 150d, new Callback< TableColumn<Immobile, Double>, TableCell<Immobile, Double> >()
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
            new DefaultTable.Column( "Propósito", "purpose", 125d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
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
                                String purpose = item == Purpose.ALL  ? "Vender/Alugar" :
                                                item == Purpose.RENT ? "Alugar"         :
                                                item == Purpose.SELL ? "Vender"         : "";

                                setText( purpose );
                            }
                            
                            else
                            {
                                setText( null );
                            }
                        }
                    };
                }
            } ),
            new DefaultTable.Column( "Dono", "ownerId", 190d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
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
                                    User user = model.com.imob.model.ModuleContext.getInstance()
                                                                                  .getUserManager()
                                                                                  .getValue( item );

                                    if( user != null )
                                    {
                                        setText( user.getName() );
                                    }
                                }

                                catch ( Exception e )
                                {
                                    Prompt.exception( e );
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
            new DefaultTable.Column( "Comprador/Locatário", "buyerId", 190d, new Callback< TableColumn<Immobile, Integer>, TableCell<Immobile, Integer> >()
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
                                    User user = model.com.imob.model.ModuleContext.getInstance()
                                                                                  .getUserManager()
                                                                                  .getValue( item );

                                    if( user != null )
                                    {
                                        setText( user.getName() );
                                    }
                                }

                                catch ( Exception e )
                                {
                                    Prompt.exception( e );
                                }
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
