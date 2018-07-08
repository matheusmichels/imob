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
import model.com.imob.model.data.Rent;
import model.com.imob.model.db.service.ClientManagerService;
import view.com.imob.view.editors.DefaultEditor;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.IntegerField;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class RentEditor
    extends
        DefaultEditor<Rent>
{
    public RentEditor( EditorCallback<Rent> callback)
    {
        super( callback );
        
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "rent.png" ) ) );
        
        initComponents();
        
        setTitle( "Editor de Aluguel" );
        setHeaderText( "Editor de Aluguel" );
        
        loadFields();
        setSource( source );
    }
    
    @Override
    protected void validadeInput( List<String> errors ) throws Exception
    {
        if ( cbRenter.getValue() == null )
        {
            errors.add( "Locatário" );
        }
        
        if ( isEmpty( ifRent.getText() ) )
        {
            errors.add( "Valor Aluguel" );
        }
        
        if ( isEmpty( ifQuantity.getText() ) )
        {
            errors.add( "Tempo de Contrato (meses)" );
        }
        
        if ( spProfitPercentage.getValue() == null )
        {
            errors.add( "Ganho Imobiliária" );
        }
    }

    @Override
    protected void obtainInput()
    {
        source.setRenterId(( (Client) cbRenter.getValue() ).getId() );
        source.setValue( Double.valueOf( ifRent.getText() ) );
        source.setMonths( Integer.valueOf( ifQuantity.getText() ) );
        source.setProfit( (Integer) spProfitPercentage.getValue() );
    }

    @Override
    protected void setSource( Rent source )
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
            cbRenter.setItems( buyersList );
            
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
    
    private void initComponents()
    {
        datebox.setDisable( true );
        tfOwner.setDisable( true );
        
        cbRenter.setPrefWidth( 190 );
        
        grid.setPadding( new Insets( 100 ) );
        grid.setStyle( "-fx-padding: 30;" );
        grid.setPrefWidth( 750 );
        grid.setVgap( 15 );
        grid.setHgap( 15 );
        
        grid.add( lbDate,          0, 0, 1, 1 );
        grid.add( datebox,         1, 0, 1, 1 );
        
        grid.add( lbOwner,         0, 1, 1, 1 );
        grid.add( tfOwner,         1, 1, 1, 1 );
        
        grid.add( lbBuyer,         2, 1, 1, 1 );
        grid.add( cbRenter,         3, 1, 1, 1 );
        
        grid.add( lbRent,      0, 2, 1, 1 );
        grid.add( ifRent,      1, 2, 1, 1 );
        
        grid.add( lbQuantity,         2, 2, 1, 1 );
        grid.add( ifQuantity,         3, 2, 1, 1 );
        
        grid.add( lbProfitPercentage,          0, 3, 1, 1 );
        grid.add( spProfitPercentage,          1, 3, 1, 1 );
        
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow( Priority.ALWAYS );
        
        grid.getColumnConstraints().addAll( new ColumnConstraints(), cc, new ColumnConstraints(), cc );
        getDialogPane().setContent( grid );
    }
    
    private GridPane grid = new GridPane();

    private Label lbOwner = new Label( "Proprietário: *" );
    private TextField tfOwner = new TextField();
    
    private Label lbBuyer = new Label( "Comprador: *" );
    private ComboBox cbRenter = new ComboBox();
    
    private Label lbDate = new Label( "Data Venda: *" );
    private DatePicker datebox = new DatePicker( LocalDate.now() );
    
    private Label lbQuantity = new Label( "Contrato (meses): *" );
    private IntegerField ifQuantity = new IntegerField();
    
    private Label lbRent = new Label( "Valor: *" );
    private IntegerField ifRent = new IntegerField();
    
    private Label lbProfitPercentage = new Label( "Ganho (%): *" );
    private Spinner spProfitPercentage = new Spinner( 0, 100, 0 );
}