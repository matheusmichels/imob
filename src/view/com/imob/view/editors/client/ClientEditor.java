/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.editors.client;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.com.imob.model.data.City;
import model.com.imob.model.data.Client;
import model.com.imob.model.data.ClientType;
import model.com.imob.model.data.State;
import model.com.imob.model.db.service.LocalManagerService;
import view.com.imob.view.editors.DefaultEditor;
import view.com.imob.view.parts.ZSeparator;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.IntegerField;
import view.com.imob.view.util.MaskedTextField;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class ClientEditor
    extends
        DefaultEditor<Client>
{
    public ClientEditor( EditorCallback<Client> callback)
    {
        super( callback );
        
        initComponents();
        
        setTitle( "Editor de Cliente" );
        setHeaderText( "Editor de Cliente" );
        
        loadComboBoxes();
        setSource( source );
    }
    
    private void loadComboBoxes()
    {
        try
        {
            List<State> states = model.com.imob.model.ModuleContext.getInstance().getLocalManager().getStates();
            stateList = FXCollections.observableArrayList( states );
            cbState.setItems( stateList );

            List<ClientType> types = ClientType.getAll();
            ObservableList<ClientType> purposeList = FXCollections.observableArrayList( types );
            cbType.setItems( purposeList );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    @Override
    protected void validadeInput(List<String> errors) throws Exception
    {
        if ( isEmpty( tfName.getText() ) )
        {
            errors.add( "Nome" );
        }
        
        if ( isEmpty( ifNumber.getText() ) )
        {
            errors.add( "Número" );
        }
        
        if ( cbType.getValue() == null )
        {
            errors.add( "Tipo" );
        }
        
        if ( isEmpty( tfStreet.getText() ) )
        {
            errors.add( "Rua" );
        }
        
        if ( isEmpty( tfDistrict.getText() ) )
        {
            errors.add( "Bairro" );
        }
        
        if ( isEmpty( mfCep.getPlainText() ) )
        {
            errors.add( "CEP" );
        }
        
        else if ( mfCep.getPlainText().length() != 8 )
        {
            errors.add( "CEP Inválido" );
        }
        
        if ( !isEmpty( mfCellular.getPlainText() ) && mfCellular.getPlainText().length() != 11 )
        {
            errors.add( "Celular Inválido" );
        }
        
        if ( !isEmpty( mfPhone.getPlainText() ) && mfPhone.getPlainText().length() != 10 )
        {
            errors.add( "Telefone Inválido" );
        }
        
        if ( !isEmpty( mfCnpj.getPlainText() ) && mfCnpj.getPlainText().length() != 14 )
        {
            errors.add( "CNPJ Inválido" );
        }
        
        if ( !isEmpty( mfRg.getPlainText() ) && mfRg.getPlainText().length() != 10 )
        {
            errors.add( "RG Inválido" );
        }
        
        if ( !isEmpty( mfCpf.getPlainText() ) && mfCpf.getPlainText().length() != 11 )
        {
            errors.add( "CPF Inválido" );
        }
        
        if ( cbState.getValue() == null )
        {
            errors.add( "Estado" );
        }
        
        if ( cbCity.getValue() == null )
        {
            errors.add( "Cidade" );
        }
        
        if ( isEmpty( ifNumber.getText() ) )
        {
            errors.add( "Número" );
        }
    }
    
    @Override
    protected void obtainInput()
    {
        source.setName( tfName.getText() );
        source.setNumber( Integer.parseInt( ifNumber.getText() ) );
        source.setStreet( tfStreet.getText() );
        source.setComplement( tfComplement.getText() );
        source.setDistrict( tfDistrict.getText() );
        source.setCityId( ( (City) cbCity.getValue() ).getId() );
        source.setCep( mfCep.getPlainText() );
        source.setEmail( tfEmail.getText() );
        source.setRg( mfRg.getPlainText() );
        source.setCpf( mfCpf.getPlainText() );
        source.setCnpj( mfCnpj.getPlainText() );
        source.setPhone( mfPhone.getPlainText() );
        source.setCellular( mfCellular.getPlainText() );
        source.setType( ( (ClientType) cbType.getValue() ).getId() );
    }

    @Override
    protected void setSource(Client source)
    {
        try
        {
            if ( source.getId() != 0 )
            {
                tfName.setText( source.getName() );
                ifNumber.setValue( source.getNumber() );
                tfStreet.setText( source.getStreet() );
                tfComplement.setText( source.getComplement() );
                tfDistrict.setText( source.getDistrict() );
                mfCep.setPlainText( source.getCep() );
                tfEmail.setText( source.getEmail() );
                mfRg.setPlainText( source.getRg() );
                mfCpf.setPlainText( source.getCpf() );
                mfCnpj.setPlainText( source.getCnpj() );
                mfPhone.setPlainText( source.getPhone() );
                mfCellular.setPlainText( source.getCellular() );

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
                }

                for ( ClientType ct : (List<ClientType>) cbType.getItems() )
                {
                    if ( ct.getId() == source.getType() )
                    {
                        cbType.setValue( ct );
                    }
                }
            }
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
        setTitle( "Editor de Cliente" );
        setHeaderText( "Editor de Cliente" );
        
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "client.png" ) ) );
        
        cbCity.setMaxWidth( 200 );
        cbCity.setMinWidth( 200 );
        
        grid.setHgap( 10 );
        grid.setVgap( 10 );
               
        grid.add( lbName, 0, 0 );
        grid.add( tfName, 1, 0 );
        grid.add( lbType, 2, 0 );
        grid.add( cbType, 3, 0 );
        
        grid.add( localSeparator, 0, 1, 5, 1 );
        
        grid.add( lbStreet, 0, 2 );
        grid.add( tfStreet, 1, 2 );
        
        grid.add( lbNumber, 2, 2 );
        grid.add( ifNumber, 3, 2 );
        
        grid.add( lbComplement, 0, 3 );
        grid.add( tfComplement, 1, 3 );
        
        grid.add( lbDistrict, 2, 3 );
        grid.add( tfDistrict, 3, 3 );
        
        //row 4
        grid.add( lbCep, 0, 4 );
        grid.add( mfCep, 1, 4 );
        grid.add( lbState, 2, 4 );
        grid.add( cbState, 3, 4 );
        grid.add( lbCity, 4, 4 );
        grid.add( cbCity, 5, 4 );
        
        //row 5
        grid.add( contactSeparator, 0, 5, 5, 1 );
        
        grid.add( lbRg, 0, 6 );
        grid.add( mfRg, 1, 6 );
        grid.add( lbCpf, 2, 6 );
        grid.add( mfCpf, 3, 6 );
        grid.add( lbCnpj, 4, 6 );
        grid.add( mfCnpj, 5, 6 );
        
        grid.add( lbEmail, 0, 7 );
        grid.add( tfEmail, 1, 7 );
        grid.add( lbPhone, 2, 7 );
        grid.add( mfPhone, 3, 7 );
        grid.add( lbCellular, 4, 7 );
        grid.add( mfCellular, 5, 7 );
        
        ColumnConstraints cc1 = new ColumnConstraints();
        ColumnConstraints cc2 = new ColumnConstraints();
        ColumnConstraints cc3 = new ColumnConstraints();
        ColumnConstraints cc4 = new ColumnConstraints();
        ColumnConstraints cc5 = new ColumnConstraints();
        ColumnConstraints cc6 = new ColumnConstraints();
        
        cc1.setPrefWidth( 100 );
        cc2.setPrefWidth( 200 );
        cc3.setPrefWidth( 100 );
        cc4.setPrefWidth( 200 );
        cc5.setPrefWidth( 100 );
        cc6.setPrefWidth( 200 );
        
        grid.getColumnConstraints().addAll( cc1, cc2, cc3, cc4, cc5, cc6 );
        
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
    
    private Label lbName = new Label( "Nome: *" );
    private TextField tfName = new TextField();
    
    private Label lbType = new Label( "Tipo: *" );
    private ComboBox cbType = new ComboBox();
    
    private ZSeparator localSeparator = new ZSeparator( "Localização" );
    
    private Label lbNumber = new Label( "Número: *" );
    private IntegerField ifNumber = new IntegerField();
    
    private Label lbComplement = new Label( "Complemento:" );
    private TextField tfComplement = new TextField();
    
    private Label lbStreet = new Label( "Rua: *" );
    private TextField tfStreet = new TextField();
    
    private Label lbDistrict = new Label( "Bairro: *" );
    private TextField tfDistrict = new TextField();
    
    private Label lbCep = new Label( "Cep: *" );
    private MaskedTextField mfCep = new MaskedTextField( "#####-###" );
    
    private Label lbState = new Label( "Estado: *" );
    private ComboBox cbState = new ComboBox();
    
    private Label lbCity = new Label( "Cidade: *" );
    private ComboBox cbCity = new ComboBox();
    
    private ZSeparator contactSeparator = new ZSeparator( "Contato" );
    
    private Label lbEmail = new Label( "Email:" );
    private TextField tfEmail = new TextField();
    
    private Label lbRg = new Label( "RG:" );
    private MaskedTextField mfRg = new MaskedTextField( "##.###.###-##" );
    
    private Label lbCpf = new Label( "CPF:" );
    private MaskedTextField mfCpf = new MaskedTextField( "###.###.###-##" );
    
    private Label lbCnpj = new Label( "CNPJ:" ); 
    private MaskedTextField mfCnpj = new MaskedTextField( "##.###.###/####-##" );
    
    private Label lbPhone = new Label( "Telefone:" );
    private MaskedTextField mfPhone = new MaskedTextField( "(##) ####-####" );
    
    private Label lbCellular = new Label( "Celular" );
    private MaskedTextField mfCellular = new MaskedTextField( "(##) #####-####" );
}
