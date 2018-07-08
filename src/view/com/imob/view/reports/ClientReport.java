/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.reports;

import com.itextpdf.text.Document;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.State;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.util.ReportToolkit;

/**
 *
 * @author matheus.michels
 */
public class ClientReport
    extends
        ReportToolkit
{
    private Client source;
    
    public ClientReport( Client source )
    {
        super( false );
        this.source = source;
    }
    
    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Cliente" );
        setSubtitle( "iMob - Gestão Imobiliária" );
        
        separator();
        newLine();
        
        LocalManagerService lm = model.com.imob.model.ModuleContext.getInstance().getLocalManager();
        
        City city = lm.getCity( source.getCityId() );
        State state = null;
        
        if ( city != null )
        {
            state = lm.getState( city.getStateId() );
        }
        
        String type = "n/d";
        
        switch ( source.getType() )
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
        
        DetailsTable details = new DetailsTable( 3f, 10f );
        details.addRow( "Nome", source.getName() );
        details.addRow( "Rua", source.getStreet() );
        details.addRow( "Número", String.valueOf( source.getNumber() ) );
        details.addRow( "Complemento", source.getComplement() );
        details.addRow( "Bairro", source.getDistrict() );
        details.addRow( "CEP", source.getCep() );
        details.addRow( "Cidade", city != null ? city.getName() : "n/d" );
        details.addRow( "Estado", state != null ? state.getName() : "n/d" );
        details.addRow( "Email", source.getEmail() != null ? source.getEmail() : "n/d" );
        details.addRow( "RG", source.getRg() != null ? source.getRg() : "n/d" );
        details.addRow( "CPF", source.getCpf() != null ? source.getCpf() : "n/d" );
        details.addRow( "CNPJ", source.getCnpj() != null ? source.getCnpj() : "n/d" );
        details.addRow( "Telefone", source.getPhone() != null ? source.getPhone() : "n/d" );
        details.addRow( "Celular", source.getCellular() != null ? source.getCellular() : "n/d" );
        details.addRow( "Tipo", type );
        details.addRow( "Status", source.getStatus() == Client.STATUS_ACTIVE ? "Ativo" : "Inativo" );
        
        document.add( details );
    }
}
