package view.com.imob.view.parts;

import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author matheus.michels
 */
public class SearchField
    extends
        HBox
{
    private boolean isPopupOpen = false;
    
    private List<String> options;
    
    public static EventType ON_SEARCH = new EventType( "onSearch" );
    
    public SearchField()
    {
        initComponents();
    }
    
    public int getIndex()
    {
        for ( int i = 0; i < options.size(); i++ )
        {
            if ( options.get( i ).equals( textfield.getPromptText() ) )
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public void setOptions( String... options )
    {
        this.options = Arrays.asList( options );
        
        ToggleGroup group = new ToggleGroup();
        
        VBox optionsBox = new VBox();
        
        for ( String s : options )
        {
            RadioButton rb = new RadioButton( s );
            rb.setToggleGroup( group );
            rb.setPadding( new Insets( 0, 2, 0, 2 ) );
            
            optionsBox.getChildren().add( rb );
            
            rb.setOnAction( new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle( ActionEvent t )
                {
                    textfield.setPromptText( s );
                    popup.hide();
                }
            } );
        }
        
        if ( group.getToggles() != null && group.getToggles().get( 0 ) != null )
        {
            group.getToggles().get( 0 ).setSelected( true );
        }
        
        popup = new PopOver( optionsBox );
        
        if ( options != null && options.length > 0 )
        {
            textfield.setPromptText( options[0] );
        }
        
        popup.setOnHiding( new EventHandler<WindowEvent>()
        {
            @Override
            public void handle( WindowEvent event )
            {
                isPopupOpen = false;
            }
        } );
        
        popup.setOnShowing( new EventHandler<WindowEvent>()
        {
            @Override
            public void handle( WindowEvent event )
            {
                isPopupOpen = true;
            }
        });
    }
    
    public String getText()
    {
        return textfield.getText();
    }
    
    private void initComponents()
    {
        setAlignment( Pos.CENTER );
        
        Tooltip searchTooltip = new Tooltip( "Buscar" );
        Tooltip.install( icon, searchTooltip );
        
        icon.setFitHeight( 30 );
        icon.setFitWidth( 30 );
        icon.setCursor( Cursor.HAND );
        
        arrow.setFitHeight( 30 );
        arrow.setFitWidth( 30 );
        arrow.setCursor( Cursor.HAND );
        
        textfield.setPrefSize( 500, 25 );
        textfield.getStyleClass().add( "textfield" );
        textfield.setLeft( arrow );
        
        setSpacing( 10 );
        getChildren().addAll( textfield, icon );
        
        textfield.textProperty().addListener((observable, oldValue, newValue) ->
        {
            fireEvent( new Event( ON_SEARCH ) );
        });
        
        arrow.setOnMouseClicked( new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if ( !isPopupOpen )
                {
                    popup.show( arrow );
                }
                else
                {
                    popup.hide();
                }
            }
        });
    }
    
    private ImageView icon = new ImageView( ResourceLocator.getImage( "search.png" ) );
    private ImageView arrow = new ImageView( ResourceLocator.getImage( "arrow.png" ) );
    private CustomTextField textfield = new CustomTextField();
    private PopOver popup;
}
