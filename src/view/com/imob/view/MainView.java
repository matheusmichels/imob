package view.com.imob.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import view.com.imob.view.panes.ActionPane;
import view.com.imob.view.panes.ActionPane.Action;
import view.com.imob.view.panes.ActionPane.ActionCategory;
import view.com.imob.view.panes.AnalyticsPane;
import view.com.imob.view.panes.HeaderPane;
import view.com.imob.view.panes.ClientPane;
import view.com.imob.view.panes.UserPane;
import view.com.imob.view.panes.DefaultPane;
import view.com.imob.view.panes.FooterPane;
import view.com.imob.view.panes.ImmobilePane;
import view.com.imob.view.panes.MenuPane;
import view.com.imob.view.panes.MenuPane.ViewButton;
import view.com.imob.view.panes.RentPane;
import view.com.imob.view.panes.SalePane;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.util.ApplicationUtilities;
import view.com.imob.view.util.EventListener;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author matheus.michels
 */
public class MainView
    extends
        Application
{
    private Stage stage;
    private DefaultPane selectedPane;

    @Override
    public void start( Stage stage ) throws Exception
    {
        menuPane = new MenuPane( Arrays.asList( buttons ) );
        
        initComponents();
        
        stage.setTitle( "Início - iMob" );
        stage.setScene( scene );
        
        stage.getIcons().add( new Image( ResourceLocator.getImage( "house.png" ) ) );
        scene.getStylesheets().add( ResourceLocator.getStylesheet( "default.css" ) );

        stage.setMaximized( true );
        stage.show();
        
        this.stage = stage;
        
        Notifications loginNotification = Notifications.create()
                .title( "Seja bem-vindo!" )
                .text( ApplicationUtilities.getInstance().getCurrentUser().getName() + " entrou no iMob - Gestão Imobiliária" )
                .graphic( new ImageView( new Image( ResourceLocator.getImage( "user.png" ) ) ) )
                .position( Pos.BOTTOM_RIGHT )
                .hideAfter( Duration.seconds( 4 ) );
        
        loginNotification.show();
    }
    
    private void refreshContent( DefaultPane pane )
    {
        selectedPane = pane;

        this.pane.setCenter( selectedPane );
        selectedPane.refreshContent();
        updateActions();
    }
    
    private void resize()
    {
        selectedPane.resize( ((Double) pane.getHeight()).intValue(), ((Double) pane.getWidth()).intValue() );
    }
    
    private void logout()
    {
        try
        {
            ApplicationUtilities.getInstance().setCurrentUser( null );

            new LoginView().start( new Stage() );
            stage.close();
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }
    
    private void updateActions()
    {
        List<ActionCategory> categories = new ArrayList();
        
        if ( selectedPane == null )
        {
            selectedPane = immobilePane;
        }
        
        if ( selectedPane.getActionCategories() != null && selectedPane.getActionCategories().size() > 0 )
        {
            categories.addAll( selectedPane.getActionCategories() );
        }
        
        categories.add( systemActions );
        
        actionPane.setActions( categories );
    }
    
    private void initComponents()
    {
        pane.setPrefSize( 1200, 700 );
        pane.setPadding( new Insets( 10 ) );
        pane.setStyle( "-fx-background-image: url(" + ResourceLocator.getImage( "background.jpg" ) + ");" 
                     + "-fx-background-size: cover, auto;" );
        
        pane.setLeft( menuPane );
        pane.setRight( actionPane );
        pane.setCenter( startPane );
        
        immobileViewButton.setSelected();
        immobilePane.refreshContent();
        pane.setCenter( immobilePane );
        updateActions();
        
        selectedPane.addEventHandler( DefaultPane.STATE_CHANGED, new EventHandler<Event>()
        {
            @Override
            public void handle(Event event)
            {
                updateActions();
            }
        } );
        
        pane.widthProperty().addListener( new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                resize();
            }
        });
        
        pane.heightProperty().addListener( new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                resize();
            }
        });
    }
    
    private BorderPane pane = new BorderPane();
    private Scene scene = new Scene( pane );

    private AnchorPane startPane = new AnchorPane();
    private MenuPane menuPane;
    private HeaderPane headerPane = new HeaderPane();
    private ActionPane actionPane = new ActionPane();
    private FooterPane footerPane = new FooterPane();
    
    private ImmobilePane immobilePane = new ImmobilePane();
    private ClientPane clientPane = new ClientPane();
    private SalePane salePane = new SalePane();
    private RentPane rentPane = new RentPane();
    private AnalyticsPane analyticsPane = new AnalyticsPane();
    private UserPane userPane = new UserPane();
    
    private ViewButton immobileViewButton = new ViewButton( "Imóveis", "immobile.png", new EventListener()
    {
        @Override
        public void onEvent()
        {
            refreshContent( immobilePane );
        }
    } );
    
    private ViewButton clientViewButton = new ViewButton( "Clientes", "client.png", new EventListener()
    {
        @Override
        public void onEvent()
        {
            refreshContent( clientPane );
        }
    } );
    
    private ViewButton saleViewButton = new ViewButton( "Vendas", "homeview.png", new EventListener()
    {
        @Override
        public void onEvent()
        {
            refreshContent( salePane );
        }
    } );
    
    private ViewButton rentViewButton = new ViewButton( "Aluguéis", "keyview.png", new EventListener()
    {
        @Override
        public void onEvent()
        {
            refreshContent( rentPane );
        }
    } );
    
    private ViewButton analyzeViewButton = new ViewButton( "Análise", "analytics.png", new EventListener()
    {
        @Override
        public void onEvent()
        {
            refreshContent( analyticsPane );
        }
    } );
    
    private ViewButton userViewButton = new ViewButton( "Usuários", "support.png", new EventListener()
    {
        @Override
        public void onEvent()
        {
            refreshContent( userPane );
        }
    } );
    
    private ViewButton[] buttons = new ViewButton[]
    {
        immobileViewButton,
        clientViewButton,
        saleViewButton,
        rentViewButton,
//        analyzeViewButton,
        userViewButton
    };
    
    private Action logoutAction = new Action( "logout.png", "Logout", "Sair da conta" )
    {
        @Override
        public void onEvent()
        {
            logout();
        }
    };
    
    private ActionCategory systemActions = new ActionCategory( "Sistema", false, new ActionPane.Action[]
    {
        logoutAction
    });
}