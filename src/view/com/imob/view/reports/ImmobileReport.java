package view.com.imob.view.reports;

import com.itextpdf.text.Document;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Purpose;
import model.com.imob.model.data.State;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.util.ReportToolkit;

/**
 *
 * @author Matheus Michels
 */
public class ImmobileReport
    extends
        ReportToolkit
{
    private Immobile source;
    
    public ImmobileReport( Immobile source )
    {
        super( true );
        this.source = source;
    }
    
    @Override
    protected void generateDocument( Document document ) throws Exception
    {
        setTitle( "Relatório de Imóvel" );
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
        
        String status = "";
        
        if ( source.getStatus() == Immobile.STATUS_AVAIABLE )
        {
            status = "Disponível para " + ( source.getPurpose() == Purpose.RENT ? "Aluguel" :
                                            source.getPurpose() == Purpose.SELL ? "Venda"   :
                                            "Venda/Aluguel" );
        }
        
        else if ( source.getStatus() == Immobile.STATUS_BOUGHT || source.getStatus() == Immobile.STATUS_RENTED )
        {
            Client client = model.com.imob.model.ModuleContext
                                            .getInstance()
                                            .getClientManager()
                                            .getValue( source.getBuyerId() );
            
//            Sale sale = model.com.imob.model.ModuleContext.getInstance().getFinanceManager()
            
            status = ( source.getStatus() == Immobile.STATUS_BOUGHT ? "Comprado" : "Alugado" ) + " por '" + client.getName() + "'";
        }
        
        DetailsTable details = new DetailsTable( 3f, 10f );
        details.addRow( "Rua", source.getStreet() );
        details.addRow( "Número", String.valueOf( source.getNumber() ) );
        details.addRow( "Complemento", source.getComplement() );
        details.addRow( "Bairro", source.getDistrict() );
        details.addRow( "Cidade", city != null ? city.getName() : "n/d" );
        details.addRow( "Estado", state != null ? state.getName() : "n/d" );
        details.addRow( "Status", status );
        
        document.add( details );
        
        newLine();
        
        DetailsTable description = new DetailsTable( 15f );
        description.addRow( "Descrição" );
        
        document.add( description );
        
        addHTML( source.getDescription() );
    }
}
