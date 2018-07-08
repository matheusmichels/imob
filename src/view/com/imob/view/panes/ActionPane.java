package view.com.imob.view.panes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
public class ActionPane
    extends
        VBox
{
    private List<ActionCategory> categories = new ArrayList();
    
    public ActionPane()
    {
        this( null );
    }
    
    public ActionPane( List<ActionCategory> categories )
    {
        this.categories = categories;
        getStylesheets().add( ResourceLocator.getStylesheet( "default.css" ) );
        setPrefWidth( 135 );
        setSpacing( 10 );
        
        refreshContent();
    }
    
    public void setActions( List<ActionCategory> categories )
    {
        this.categories = categories;
        
        refreshContent();
    }
    
    private void refreshContent()
    {
        getChildren().clear();
        
        if ( categories != null && !categories.isEmpty() )
        {
            for ( ActionCategory c : categories )
            {
                getChildren().add( c );
            }
        }
    }
    
    public static abstract class Action
        extends
            HBox
        implements
            EventListener
    {
        private Label label = new Label();
        private ImageView icon = new ImageView();
        private Tooltip tooltip = new Tooltip();
        
        public Action( String icon, String label, String tooltip )
        {
            this.icon.setImage( new Image( ResourceLocator.getImage( icon ) ) );
            this.label.setText( label );
            this.tooltip.setText( tooltip );

            initComponents();
        }
        
        private void initComponents()
        {
            label.getStyleClass().add( "action-label" );
            icon.getStyleClass().add( "action-icon" );
            
            icon.setFitHeight( 25 );
            icon.setFitWidth( 25 );
            
            Tooltip.install( this, tooltip );
            
            setAlignment( Pos.CENTER_LEFT );
            setPadding( new Insets( 0, 0, 0, 10 ) );
            setCursor( Cursor.HAND );
            getChildren().addAll( icon, label );
            
            setOnMouseClicked( new EventHandler<MouseEvent>()
            {
                @Override
                public void handle( MouseEvent event )
                {
                    onEvent();
                }
            } );
        }
    }
    
    public static class ActionCategory
        extends
            VBox
    {
        private Label label = new Label();
        private List<Action> actions;
        private boolean expanded;
        private ImageView icon = new ImageView();
        private HBox category = new HBox();
        
        public ActionCategory( String label, Action... actions )
        {
            this( label, true, actions );
        }
        
        public ActionCategory( String label, boolean expanded, Action... actions )
        {
            this.label.setText( label );
            this.actions = Arrays.asList( actions );
            this.expanded = expanded;
            
            initComponents();
        }
        
        private void refreshActions()
        {
            icon.setImage( new Image( expanded ? ResourceLocator.getImage( "minus.png" ) : 
                                                 ResourceLocator.getImage( "plus.png" ) ) );
            
            getChildren().clear();
            getChildren().add( category );
            
            if ( actions != null && !actions.isEmpty() && expanded )
            {
                for ( Action b : actions )
                {
                    getChildren().add( b );
                }
            }
        }
        
        private void initComponents()
        {
            label.getStyleClass().add( "category-label" );
            getStyleClass().add( "category" );
            
            category.setSpacing( 4 );
            category.setAlignment( Pos.CENTER_LEFT );
            category.getChildren().addAll( icon, label );
            
            icon.setImage( new Image( ResourceLocator.getImage( "minus.png" ) ) );
            icon.setFitHeight( 25 );
            icon.setFitWidth( 25 );

            setSpacing( 3 );
            
            refreshActions();
            
            category.setOnMouseClicked( new EventHandler<MouseEvent>()
            {
                @Override
                public void handle( MouseEvent event )
                {
                    expanded = !expanded;
                    refreshActions();
                }
            } );
        }
    }
}