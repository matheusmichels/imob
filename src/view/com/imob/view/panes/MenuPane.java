package view.com.imob.view.panes;

import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.com.imob.view.util.EventListener;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author matheus.michels
 */
public class MenuPane
    extends
        VBox
{
    private List<ViewButton> buttons;
    
    public MenuPane()
    {
        this( null );
    }
    
    public MenuPane( List<ViewButton> buttons )
    {
        this.buttons = buttons;
        getStylesheets().add( ResourceLocator.getStylesheet( "default.css" ) );
        
        initComponents();
    }
    
    private void performSelect( ViewButton button )
    {
        for ( ViewButton b : buttons )
        {
            if ( b == button )
            {
                b.event.onEvent();
                b.setStyle( "-fx-background-color: grey; -fx-font-weight: bold;" );
            }
            
            else
            {
                b.setStyle( "-fx-background-color: transparent; -fx-font-weight: normal;" );
            }
        }
    }
    
    private void initComponents()
    {
        if ( buttons != null && !buttons.isEmpty() )
        {
            setSpacing( 2 );
            
            for ( final ViewButton b : buttons )
            {
                getChildren().addAll( b );
                b.addEventHandler( ViewButton.ON_CLICK, new EventHandler<Event>()
                {
                    @Override
                    public void handle(Event event)
                    {
                        performSelect( b );
                    }
                } );
            }
        }
    }
    
    public static class ViewButton
        extends
            HBox
    {
        private static final EventType ON_CLICK = new EventType( "onClick" );
        
        private Label label = new Label();
        private ImageView icon = new ImageView();
        private EventListener event;
        
        public ViewButton( String label, String icon, EventListener event )
        {
            this.label.setText( label );
            this.icon.setImage( new Image( ResourceLocator.getImage( icon ) ) );
            this.event = event;
            
            initComponents();
        }
        
        public void setSelected()
        {
            fireEvent( new Event( ON_CLICK ) );
        }
        
        private void initComponents()
        {
            label.getStyleClass().add( "button-label" );
            icon.getStyleClass().add( "button-icon" );
            
            icon.setFitHeight( 40 );
            icon.setFitWidth( 40 );
            
            setAlignment( Pos.CENTER_LEFT );
            setSpacing( 5 );
            setCursor( Cursor.HAND );
            getChildren().addAll( icon, label );
            
            setOnMouseClicked( new EventHandler<MouseEvent>()
            {
                @Override
                public void handle( MouseEvent event )
                {
                    fireEvent( new Event( ON_CLICK ) );
                }
            } );
        }
    }
}