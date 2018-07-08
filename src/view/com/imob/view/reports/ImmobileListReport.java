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
 * @author Matheus Michels
 */
public class ImmobileListReport
    extends
        ReportToolkit
{
    private List<Immobile> immobiles;
    
    private final String[] header = new String[]
    {
        "#",
        "Status",
        "Rua",
        "Nº",
        "Bairro",
        "Cidade",
        "Estado",
        "CEP",
        "Valor\nR$",
        "Aluguel\nR$",
        "Propósito",
        "Dono",
        "Comprador/\nLocatário"
    };
    
    public ImmobileListReport( List<Immobile> immobiles )
    {
        super( true );
        this.immobiles = immobiles;
    }
    
    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Listagem de Imóveis" );
        setSubtitle( "iMob - Gestão Imobiliária" );
        
        separator();
        newLine();
        
        Table table = new Table( 1.5f, 4f, 8f, 2.5f, 7f, 7f, 3.5f, 4f, 4.5f, 4.5f, 4.5f, 7f, 7f );
        table.setHeader( header );
        
        for ( Immobile i : immobiles )
        {
            String status = i.getStatus() == Immobile.STATUS_AVAIABLE ? "Disponível" :
                            i.getStatus() == Immobile.STATUS_RESERVED ? "Reservado"  :
                            i.getStatus() == Immobile.STATUS_RENTED   ? "Alugado"    :
                            i.getStatus() == Immobile.STATUS_BOUGHT   ? "Vendido" : "";
            
            String purpose = i.getPurpose() == Purpose.RENT ? "Alugar" :
                            i.getPurpose() == Purpose.SELL  ? "Vender" : "Todos";
            
            ClientManagerService cm = ClientManagerService.getInstance();
            
            Client owner = cm.getValue( i.getOwnerId() );
            Client buyer = cm.getValue( i.getBuyerId() );
          
            LocalManagerService lm = LocalManagerService.getInstance();
            
            City city = lm.getCity( i.getCityId() );
            State state = null;
            
            if ( city != null )
            {
                state = lm.getState( city.getStateId() );
            }
            
            table.addRow( String.valueOf( i.getId() ),
                          status,
                          i.getStreet(),
                          String.valueOf( i.getNumber() ),
                          i.getDistrict(),
                          city != null ? city.getName() : "n/d",
                          state != null ? state.getUf() : "n/d",
                          i.getCep() != null ? i.getCep() : "n/d",
                          String.valueOf( i.getValue() ),
                          String.valueOf( i.getRent() ),
                          purpose,
                          owner != null ? owner.getName() : "n/d",
                          buyer != null ? buyer.getName() : "n/d" );
        }
        
        document.add( table );
    }
}