package view.com.imob.view.editors.user;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.com.imob.model.data.City;
import model.com.imob.model.data.State;
import model.com.imob.model.data.User;
import view.com.imob.view.editors.DefaultEditor;
import view.com.imob.view.util.EditorCallback;
import view.com.imob.view.util.ResourceLocator;
import view.com.imob.view.util.Util;
import view.com.imob.view.util.Validator;

/**
 *
 * @author Matheus Michels
 */
public class UserEditor
    extends
        DefaultEditor<User>
{

    public UserEditor( EditorCallback<User> callback )
    {
        super( callback );
        
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "house" ) ) );
        
        initComponents();
        
        setTitle( "Editor de Usuário" );
        setHeaderText( "Editor de Usuário" );
        
        setSource( source );
    }

    @Override
    protected void validadeInput( List<String> errors ) throws Exception
    {
        if ( isEmpty( tfName.getText() ) )
        {
            errors.add( "Nome" );
        }
        
        if ( !isEmpty( tfEmail.getText() ) && !Validator.email( tfEmail.getText().trim() ) )
        {
            errors.add( "Digite um email válido!" );
        }
        
        if ( isEmpty( tfLogin.getText() ) )
        {
            errors.add( "Login" );
        }
        
        if ( isEmpty( tfPassword.getText() ) )
        {
            errors.add( "Senha" );
        }
        
        for ( User u : model.com.imob.model.ModuleContext.getInstance().getUserManager().getValues() )
        {
            User current = (User) callback.getSource();
            
            if ( current.getId() == 0 )
            {
                if ( tfLogin.getText().trim().equals( u.getLogin() ) )
                {
                    errors.add( "Esse login já está em uso!" );
                }
            }
            
            else
            {
                if ( current.getId() != u.getId() && tfLogin.getText().trim().equals( u.getLogin() ) )
                {
                    errors.add( "Esse login já está em uso!" );
                }
            }
        }
    }

    @Override
    protected void obtainInput()
    {
        source.setName( tfName.getText() );
        source.setEmail( tfEmail.getText() );
        source.setLogin( tfLogin.getText() );
        
        if ( source.getId() != 0 )
        {
            if ( !source.getPassword().equals( tfPassword.getText() ) )
            {
                source.setPassword( Util.hash( tfPassword.getText() ) );
            }
        }

        else
        {
            source.setPassword( Util.hash( tfPassword.getText() ) );
        }
    }

    @Override
    protected void setSource( User source )
    {
        if ( source.getId() != 0 )
        {
            tfName.setText( source.getName() );
            tfEmail.setText( source.getEmail() );
            tfLogin.setText( source.getLogin() );
            tfPassword.setText( source.getPassword() );
        }
    }
    
    private void initComponents()
    {
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "user.png" ) ) );
        
        grid.setPadding( new Insets( 500 ) );
        grid.setStyle( "-fx-padding: 30;" );
        grid.setPrefWidth( 350 );
        grid.setVgap( 15 );
        grid.setHgap( 15 );
        
        grid.add( lbName,         0, 0, 1, 1 );
        grid.add( tfName,         1, 0, 1, 1 );
        
        grid.add( lbEmail,         0, 1, 1, 1 );
        grid.add( tfEmail,         1, 1, 1, 1 );
        
        grid.add( lbLogin,      0, 2, 1, 1 );
        grid.add( tfLogin,      1, 2, 1, 1 );

        grid.add( lbPassword,        0, 3, 1, 1 );
        grid.add( tfPassword,        1, 3, 1, 1 );
        
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow( Priority.ALWAYS );
        
        grid.getColumnConstraints().addAll( new ColumnConstraints(), cc );
        getDialogPane().setContent( grid );
    }
    
    ObservableList<State> stateList;
    ObservableList<City> citiesList;

    private GridPane grid = new GridPane();
    
    private Label lbName = new Label( "Nome: *" );
    private TextField tfName = new TextField();
    
    private Label lbEmail = new Label( "Email:" );
    private TextField tfEmail = new TextField();
    
    private Label lbLogin = new Label( "Login: *" );
    private TextField tfLogin = new TextField();
    
    private Label lbPassword = new Label( "Senha: *" );
    private PasswordField tfPassword = new PasswordField();
}