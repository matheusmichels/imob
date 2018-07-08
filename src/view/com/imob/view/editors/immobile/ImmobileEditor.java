package view.com.imob.view.editors.immobile;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.Immobile;
import model.com.imob.model.data.Purpose;
import model.com.imob.model.data.State;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.editors.DefaultEditor;
import view.com.imob.view.util.ComboBoxAutoComplete;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.IntegerField;
import view.com.imob.view.util.MaskedTextField;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class ImmobileEditor
    extends
        DefaultEditor<Immobile>
{

    public ImmobileEditor( EditorCallback<Immobile> callback )
    {
        super( callback );
        
        initComponents();
        
        loadComboBoxes();
        setSource( source );
    }

    @Override
    protected void validadeInput( List<String> errors ) throws Exception
    {
        if ( isEmpty( tfStreet.getText() ) )
        {
            errors.add( "Rua" );
        }
        
        if ( isEmpty( ifNumber.getText() ) )
        {
            errors.add( "Número" );
        }
        
        if ( isEmpty( tfDistrict.getText() ) )
        {
            errors.add( "Bairro" );
        }
        
        if ( cbCity.getValue() == null )
        {
            errors.add( "Cidade" );
        }
        
        if ( isEmpty( mfCep.getPlainText() ) )
        {
            errors.add( "CEP" );
        }
        
        else if ( mfCep.getPlainText().length() != 8 )
        {
            errors.add( "CEP Inválido" );
        }
        
        if ( cbOwner.getValue() == null )
        {
            errors.add( "Proprietário" );
        }
        
        if ( cbPurpose.getValue() == null )
        {
            errors.add( "Objetivo" );
        }

        if ( isEmpty( ifValue.getText() ) && isEmpty( ifRent.getText() ) )
        {
            errors.add( "Valor e/ou Aluguel" );
        }
    }

    @Override
    protected void obtainInput()
    {
        source.setStreet( tfStreet.getText() );
        source.setNumber( Integer.parseInt( ifNumber.getText() ) );
        source.setComplement( tfComplement.getText() );
        source.setDistrict( tfDistrict.getText() );
        source.setCityId( ( (City) cbCity.getValue() ).getId() );
        source.setCep( mfCep.getPlainText() );
        source.setOwnerId( ( (Client) cbOwner.getValue() ).getId() );
        source.setPurpose( ( (Purpose) cbPurpose.getValue() ).getId() );
        source.setDescription( taDescription.getHtmlText() );
        
        if ( !isEmpty(ifValue.getText()))
        {
            source.setValue( Double.parseDouble( ifValue.getText() ) );
        }
        
        else
        {
            source.setValue( 0.0 );
        }
        
        if ( !isEmpty(ifRent.getText()))
        {
            source.setRent( Double.parseDouble( ifRent.getText() ) );
        }
        
        else
        {
            source.setRent( 0.0 );
        }
    }

    @Override
    protected void setSource( Immobile source )
    {
        try
        {
            if ( source.getId() != 0 )
            {
                tfStreet.setText( source.getStreet() );
                ifNumber.setValue( source.getNumber() );
                tfComplement.setText( source.getComplement() );
                tfDistrict.setText( source.getDistrict() );
                mfCep.setPlainText( source.getCep() );
                ifValue.setValue( source.getValue() );
                ifRent.setValue( source.getRent() );
                taDescription.setHtmlText( source.getDescription() );
                
                LocalManagerService lm = model.com.imob.model.ModuleContext.getInstance().getLocalManager();
                
                City city = lm.getCity( source.getCityId() );

                if ( city != null )
                {
                    State state = lm.getState( city.getStateId() );

                    if ( state != null )
                    {
                        cbState.setValue( state );
                        updateCities();

                        cbCity.setValue( city );
                    }

                    if ( source.getOwnerId() != 0 )
                    {
                        Client owner = model.com.imob.model.ModuleContext.getInstance().getClientManager().getValue( source.getOwnerId() );

                        if ( owner != null )
                        {
                            cbOwner.setValue( owner );
                        }
                    }
                }
                
                for ( Purpose p : (List<Purpose>) cbPurpose.getItems() )
                {
                    if ( p.getId() == source.getPurpose() )
                    {
                        cbPurpose.setValue( p );
                    }
                }
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void loadComboBoxes()
    {
        try
        {
            List<State> states = model.com.imob.model.ModuleContext.getInstance().getLocalManager().getStates();
            stateList = FXCollections.observableArrayList( states );
            cbState.setItems( stateList );
            
            List<Client> owners = model.com.imob.model.ModuleContext.getInstance().getClientManager().getOwners();
            ObservableList<Client> ownersList = FXCollections.observableArrayList( owners );
            cbOwner.setItems( ownersList );

            List<Purpose> purposes = Purpose.getAll();
            ObservableList<Purpose> purposeList = FXCollections.observableArrayList( purposes );
            cbPurpose.setItems( purposeList );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void updateCities()
    {
        try
        {
            if ( cbState.getValue() != null )
            {
                List<City> cities = model.com.imob.model.ModuleContext.getInstance().getLocalManager().getCities( ( (State) cbState.getValue() ).getId() );
                
                if ( cities != null && !cities.isEmpty() )
                {
                    citiesList = FXCollections.observableArrayList( cities );
                    cbCity.setItems( citiesList );
                }
            }
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    private void initComponents()
    {
        setTitle( "Editor de Imóvel" );
        setHeaderText( "Editor de Imóvel" );
        
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "immobile.png" ) ) );
        
        grid.setPadding( new Insets( 400 ) );
        grid.setStyle( "-fx-padding: 30;" );
        grid.setPrefWidth( 800 );
        grid.setVgap( 15 );
        grid.setHgap( 15 );
        
        taDescription.setPrefHeight( 250 );
        ifNumber.setPrefWidth( 50 );
        mfCep.setPrefWidth( 120 );
        
        lbState.setMaxWidth( 100 );
        lbState.setMinWidth( 100 );
        
        cbOwner.setMinWidth( 250 );
        cbOwner.setMaxWidth( 250 );
        
        cbPurpose.setMinWidth( 250 );
        cbPurpose.setMaxWidth( 250 );
        
        cbCity.setMaxWidth( 170 );
        cbCity.setMinWidth( 170 );
        
        cbState.setMaxWidth( 80 );
        cbState.setMinWidth( 80 );
        
        ifValue.setPromptText( "R$" );
        ifRent.setPromptText( "R$" );

        //1
        grid.add( lbStreet,         0, 0, 1, 1 );
        grid.add( tfStreet,         1, 0, 3, 1 );
        
        grid.add( lbNumber,         4, 0, 1, 1 );
        grid.add( ifNumber,         5, 0, 1, 1 );
        
        //2
        grid.add( lbDistrict,        0, 1, 1, 1 );
        grid.add( tfDistrict,        1, 1, 2, 1 );
        
        grid.add( lbComplement,      3, 1, 1, 1 );
        grid.add( tfComplement,      4, 1, 2, 1 );

        //3
        grid.add( lbCep,       0, 2, 1, 1 );
        grid.add( mfCep,       1, 2, 1, 1 );
        
        grid.add( lbState,           2, 2, 1, 1 );
        grid.add( cbState,           3, 2, 1, 1 );
        
        grid.add( lbCity,            4, 2, 1, 1 );
        grid.add( cbCity,            5, 2, 1, 1 );
        
        //4
        grid.add( lbOwner,     0, 3, 1, 1 );
        grid.add( cbOwner,     1, 3, 2, 1 );
     
        grid.add( lbPurpose,      3, 3, 1, 1 );
        grid.add( cbPurpose,      4, 3, 2, 1 );

        //5
        grid.add( lbValue,        0, 4, 1, 1 );
        grid.add( ifValue,        1, 4, 2, 1 );
        
        grid.add( lbRent,        3, 4, 1, 1 );
        grid.add( ifRent,        4, 4, 2, 1 );
        
        //6
        grid.add( lbDescription,   0, 5, 1, 1 );
        grid.add( taDescription,   1, 5, 5, 1 );
        
        getDialogPane().setContent( grid );
        
        cbState.setOnAction( new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                updateCities();
            }
        });
    }
    
    ObservableList<State> stateList;
    ObservableList<City> citiesList;

    private GridPane grid = new GridPane();
    
    private Label lbStreet = new Label( "Rua: *" );
    private TextField tfStreet = new TextField();
    
    private Label lbNumber = new Label( "Nº: *" );
    private IntegerField ifNumber = new IntegerField();
    
    private Label lbComplement = new Label( "Complemento:" );
    private TextField tfComplement = new TextField();
    
    private Label lbDistrict = new Label( "Bairro: *" );
    private TextField tfDistrict = new TextField();
    
    private Label lbState = new Label( "Estado: *" );
    private ComboBox<State> cbState = new ComboBox();
    
    private Label lbCity = new Label( "Cidade: *" );
    private ComboBox<City> cbCity = new ComboBox();
    
    private Label lbCep = new Label( "CEP: *" );
    private MaskedTextField mfCep = new MaskedTextField( "#####-###" );
    
    private Label lbOwner = new Label( "Proprietário: *" );
    private ComboBox cbOwner = new ComboBox();
    
    private Label lbPurpose = new Label( "Propósito: *" );
    private ComboBox cbPurpose = new ComboBox();
    
    private Label lbValue = new Label( "Valor:" );
    private IntegerField ifValue = new IntegerField();
    
    private Label lbRent = new Label( "Aluguel:" );
    private IntegerField ifRent = new IntegerField();
    
    private Label lbDescription = new Label( "Descrição:" );
    private HTMLEditor taDescription = new HTMLEditor();
}