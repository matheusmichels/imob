package view.com.imob.view.editors.immobile;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.Sale;
import model.com.imob.model.db.service.ClientManagerService;
import view.com.imob.view.editors.DefaultEditor;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.IntegerField;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class SaleEditor
    extends
        DefaultEditor<Sale>
{
    public SaleEditor( EditorCallback<Sale> callback)
    {
        super( callback );
        
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "sell.png" ) ) );
        
        initComponents();
        
        setTitle( "Editor de Venda" );
        setHeaderText( "Editor de Venda" );
        
        loadFields();
        setSource( source );
    }
    
    @Override
    protected void validadeInput( List<String> errors ) throws Exception
    {
        if ( cbBuyer.getValue() == null )
        {
            errors.add( "Comprador" );
        }
        
        if ( isEmpty( ifTotal.getText() ) )
        {
            errors.add( "Valor" );
        }
        
        if ( isEmpty( ifQuantity.getText() ) )
        {
            errors.add( "Parcelas" );
        }
        
        if ( isEmpty( ifPlot.getText() ) )
        {
            errors.add( "Valor parcela" );
        }
        
        if ( spProfitPercentage.getValue() == null )
        {
            errors.add( "Ganho Imobiliária" );
        }
    }

    @Override
    protected void obtainInput()
    {
        source.setBuyerId( ( (Client) cbBuyer.getValue() ).getId() );
        source.setValue( Double.valueOf( ifTotal.getText() ) );
        source.setPlots( Integer.valueOf( ifQuantity.getText() ) );
        source.setProfit( (Integer) spProfitPercentage.getValue() );
    }

    @Override
    protected void setSource( Sale source )
    {
        //do nothing
    }
    
    private void loadFields()
    {
        try
        {
            ClientManagerService cm = model.com.imob.model.ModuleContext.getInstance().getClientManager();
            
            List<Client> buyers = cm.getBuyers();
            List<Client> filtered = new ArrayList();
            
            for ( Client c : buyers )
            {
                if ( c.getId() != source.getOwnerId() )
                {
                    filtered.add( c );
                }
            }
            
            ObservableList<Client> buyersList = FXCollections.observableArrayList( filtered );
            cbBuyer.setItems( buyersList );
            
            Client owner = cm.getValue( source.getOwnerId() );
            
            if ( owner != null )
            {
                tfOwner.setText( owner.getName() );
            }
            
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void updatePlot( String totalText, String quantityText )
    {
        double total = 0, quantity = 0;
        double plot = 0;
        
        if ( !isEmpty( totalText ) )
        {
            total = Double.valueOf( totalText );
        }
        
        if ( !isEmpty( quantityText ) )
        {
            quantity = Double.valueOf( quantityText ); 
        }
        
        if ( total != 0 && quantity != 0 )
        {
            plot = total/quantity;
        }
        
        DecimalFormat df = new DecimalFormat( "#.##" );
        
        ifPlot.setText( df.format( plot ) );
    }
    
    private void initComponents()
    {
        datebox.setDisable( true );
        tfOwner.setDisable( true );
        ifPlot.setDisable( true );
        
        cbBuyer.setPrefWidth( 190 );
        
        grid.setPadding( new Insets( 100 ) );
        grid.setStyle( "-fx-padding: 30;" );
        grid.setPrefWidth( 700 );
        grid.setVgap( 15 );
        grid.setHgap( 15 );
        
        grid.add( lbDate,          0, 0, 1, 1 );
        grid.add( datebox,         1, 0, 1, 1 );
        
        grid.add( lbOwner,         0, 1, 1, 1 );
        grid.add( tfOwner,         1, 1, 1, 1 );
        
        grid.add( lbBuyer,         2, 1, 1, 1 );
        grid.add( cbBuyer,         3, 1, 1, 1 );
        
        grid.add( lbTotal,      0, 2, 1, 1 );
        grid.add( ifTotal,      1, 2, 1, 1 );
        
        grid.add( lbQuantity,         2, 2, 1, 1 );
        grid.add( ifQuantity,         3, 2, 1, 1 );
        
        grid.add( lbPlot,          0, 3, 1, 1 );
        grid.add( ifPlot,          1, 3, 1, 1 );
        
        grid.add( lbProfitPercentage,      2, 3, 1, 1 );
        grid.add( spProfitPercentage,      3, 3, 1, 1 );
        
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow( Priority.ALWAYS );
        
        grid.getColumnConstraints().addAll( new ColumnConstraints(), cc, new ColumnConstraints(), cc );
        getDialogPane().setContent( grid );
        
        ifQuantity.textProperty().addListener((observable, oldValue, newValue) ->
        {
            updatePlot( ifTotal.getText(), newValue );
        });
        
        ifTotal.textProperty().addListener((observable, oldValue, newValue) ->
        {
            updatePlot( newValue, ifQuantity.getText() );
        });
    }
    
    private GridPane grid = new GridPane();

    private Label lbOwner = new Label( "Proprietário: *" );
    private TextField tfOwner = new TextField();
    
    private Label lbBuyer = new Label( "Comprador: *" );
    private ComboBox cbBuyer = new ComboBox();
    
    private Label lbDate = new Label( "Data Venda: *" );
    private DatePicker datebox = new DatePicker( LocalDate.now() );
    
    private Label lbQuantity = new Label( "Parcelas: *" );
    private IntegerField ifQuantity = new IntegerField();
    
    private Label lbTotal = new Label( "Valor Total: *" );
    private IntegerField ifTotal = new IntegerField();
    
    private Label lbPlot = new Label( "Valor Parcela:" );
    private IntegerField ifPlot = new IntegerField();
    
    private Label lbProfitPercentage = new Label( "Ganho (%): *" );
    private Spinner spProfitPercentage = new Spinner( 0, 100, 0 );
}