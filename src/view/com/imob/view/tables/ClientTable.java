/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.tables;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import view.com.imob.view.parts.Prompt;

/**
 *
 * @author matheus.michels
 */
public class ClientTable
    extends
        DefaultTable<Client>
{

    public ClientTable()
    {
        setColumns
        (
            new DefaultTable.Column( "#", "id", 30d ),
            new DefaultTable.Column( "Nome", "name", 180d ),
            new DefaultTable.Column( "Tipo", "type", 100d, new Callback< TableColumn<Client, Integer>, TableCell<Client, Integer> >()
            {
                @Override
                public TableCell<Client, Integer> call( TableColumn<Client, Integer> param )
                {
                    return new TableCell<Client, Integer>() 
                    {
                        @Override
                        protected void updateItem( Integer item, boolean empty )
                        {
                            super.updateItem( item, empty );

                            if( ! empty )
                            {
                                String status = item == Client.TYPE_ALL    ? "P/C"              :
                                                item == Client.TYPE_BUYER  ? "Comprador"      :
                                                item == Client.TYPE_SELLER ? "Proprietário"      : "";

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
            new DefaultTable.Column( "Telefone", "phone", 110d ),
            new DefaultTable.Column( "Celular", "cellular", 110d ),
            new DefaultTable.Column( "RG", "rg", 110d ),
            new DefaultTable.Column( "CPF", "cpf", 110d ),
            new DefaultTable.Column( "CNPJ", "cnpj", 110d ),
            new DefaultTable.Column( "Número", "number", 85d ),
            new DefaultTable.Column( "Rua", "street", 250d ),
            new DefaultTable.Column( "Bairro", "district", 210d ),
            new DefaultTable.Column( "Cidade", "cityId", 210d, new Callback< TableColumn<Client, Integer>, TableCell<Client, Integer> >()
            {
                @Override
                public TableCell<Client, Integer> call( TableColumn<Client, Integer> param )
                {
                    return new TableCell<Client, Integer>() 
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
            new DefaultTable.Column( "CEP", "cep", 110d )
        );
    }
}
