/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.reports;

import com.itextpdf.text.Document;
import java.util.List;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Purpose;
import model.com.imob.model.data.State;
import model.com.imob.model.db.service.ClientManagerService;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.util.ReportToolkit;

/**
 *
 * @author matheus.michels
 */
public class ClientListReport
    extends
        ReportToolkit
{
    private List<Client> clients;
    
    private final String[] header = new String[]
    {
        "#",
        "Nome",
        "Rua",
        "Nº",
        "Bairro",
        "Cidade",
        "UF",
        "CEP",
        "Email",
        "RG",
        "CPF",
        "CNPJ",
        "Telefone",
        "Celular",
        "Tipo"
    };
    
    public ClientListReport( List<Client> clients ) 
    {
        super( true );
        this.clients = clients;
    }
    
    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Clientes" );
        setSubtitle( "iMob - Gestão Imobiliária" );
        
        separator();
        newLine();
        
        Table table = new Table( 1.5f, 4f, 8f, 2.5f, 7f, 7f, 3.5f, 4f, 4.5f, 4.5f, 4.5f, 7f, 7f, 6f, 7f );
        table.setHeader( header );
        
        for ( Client c : clients )
        {
            String type = "n/d";
        
            switch ( c.getType() )
            {
                case Client.TYPE_ALL:
                    type = "Proprietário/Comprador";
                    break;
                case Client.TYPE_BUYER:
                    type = "Comprador";
                    break;
                case Client.TYPE_SELLER:
                    type = "Proprietário";
                    break;
                default:
                    break;
            }
            
            LocalManagerService lm = LocalManagerService.getInstance();
            
            City city = lm.getCity( c.getCityId() );
            State state = null;
            
            if ( city != null )
            {
                state = lm.getState( city.getStateId() );
            }
            
            table.addRow( String.valueOf( c.getId() ),
                          c.getName(),
                          c.getStreet(),
                          String.valueOf( c.getNumber() ),
                          c.getDistrict(),
                          city != null ? city.getName() : "n/d",
                          state != null ? state.getUf() : "n/d",
                          c.getCep() != null ? c.getCep() : "n/d",
                          c.getEmail() != null ? c.getEmail() : "n/d",
                          c.getRg() != null ? c.getRg() : "n/d",
                          c.getCpf() != null ? c.getCpf() : "n/d",
                          c.getCnpj() != null ? c.getCnpj() : "n/d",
                          c.getPhone() != null ? c.getPhone() : "n/d",
                          c.getCellular() != null ? c.getCellular() : "n/d",
                          type );
        }
        
        document.add( table );
    }
}