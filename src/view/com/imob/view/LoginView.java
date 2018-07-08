package view.com.imob.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.com.imob.model.data.User;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.util.ApplicationUtilities;
import view.com.imob.view.util.ResourceLocator;
import view.com.imob.view.util.Util;

/**
 *
 * @author matheus.michels
 */
public class LoginView
    extends
        Application
{
    private Stage stage;
    
    public static void main( String[] args )
    {
        launch( args );
    }

    @Override
    public void start( Stage stage ) throws Exception
    {
        initComponents();
        
        stage.setTitle( "Login - iMob" );
        stage.setScene( scene );
        
        stage.getIcons().add( new Image( ResourceLocator.getImage( "house.png" ) ) );
        scene.getStylesheets().add( ResourceLocator.getStylesheet( "default.css" ) );

        stage.setResizable( false );
        stage.show();
        
        this.stage = stage;
    }
    
    private void login()
    {
        try
        {
            User user = model.com.imob.model.ModuleContext
                                            .getInstance()
                                            .getUserManager()
                                            .getUserByLogin( tfLogin.getText(),
                                                             Util.hash( tfPassword.getText() ) );
            
            if ( user != null )
            {
                ApplicationUtilities.getInstance().setCurrentUser( user );
                
                new MainView().start( new Stage() );
                stage.close();
            }
            
            else
            {
                Prompt.alert( "Usuário e/ou senha incorreto(s)!" );
                
                tfLogin.setText( "" );
                tfPassword.setText( "" );
                tfLogin.requestFocus();
            }
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }
    
    public void stilify()
    {
        vbox.setPadding( new Insets( 100, 200, 100, 200 ) );
        
        imobIcon.setFitHeight( 100 );
        imobIcon.setFitWidth( 150 );
    
        loginIcon.setFitHeight( 50 );
        loginIcon.setFitWidth( 50 );
        
        passwordIcon.setFitHeight( 50 );
        passwordIcon.setFitWidth( 50 );
        
        tfLogin.getStyleClass().add( "login" );
        tfLogin.setPrefSize( 250, 30 );
        
        tfPassword.getStyleClass().add( "password" );
        tfPassword.setPrefSize( 250, 30 );
        
        btLogin.getStyleClass().add( "button" );
        btLogin.setPrefSize( 150, 30 );
    }

    public void initComponents()
    {
        pane.setStyle( "-fx-background-image: url(" + ResourceLocator.getImage( "background.jpg" ) + ");" 
                     + "-fx-background-size: cover, auto;" );
        
        imobIcon.setImage( new Image( ResourceLocator.getImage( "logo.png" ) ) );
        loginIcon.setImage( new Image( ResourceLocator.getImage( "user.png" ) ) );
        passwordIcon.setImage( new Image( ResourceLocator.getImage( "lock.png" ) ) );
        
        tfLogin.setPromptText( "Usuário" );
        tfPassword.setPromptText( "Senha" );
        btLogin.setText( "Entrar" );
        
        stilify();
        
        btLogin.setCursor( Cursor.HAND );
        
        HBox loginHBox = new HBox();
        loginHBox.setSpacing( 10 );
        loginHBox.getChildren().addAll( loginIcon, tfLogin );
        
        HBox passwordHBox = new HBox();
        passwordHBox.setSpacing( 10 );
        passwordHBox.getChildren().addAll( passwordIcon, tfPassword );
        
        vbox.autosize();
        vbox.setSpacing( 10 );
        vbox.setAlignment( Pos.BASELINE_CENTER );
        vbox.getChildren().addAll( imobIcon, loginHBox, passwordHBox, btLogin );
            
        pane.setPrefSize( 500, 250 );
        pane.getChildren().add( vbox );
        
        tfPassword.setOnKeyPressed( new EventHandler<KeyEvent>()
        {
            @Override
            public void handle( KeyEvent t ) 
            {
                if ( t.getCode().equals( KeyCode.ENTER ) )
                {
                    login();
                }
            }
        } );
        
        btLogin.setOnAction( new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle( ActionEvent t )
            {
                login();
            }
        } );
    }
    
    private AnchorPane pane = new AnchorPane();
    private Scene scene = new Scene( pane );
    
    private VBox vbox = new VBox();
    
    private ImageView imobIcon = new ImageView();
    
    private ImageView loginIcon = new ImageView();
    private TextField tfLogin = new TextField();
    
    private ImageView passwordIcon = new ImageView();
    private PasswordField tfPassword = new PasswordField();
    
    private Button btLogin = new Button();
}