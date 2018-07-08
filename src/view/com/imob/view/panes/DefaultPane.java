package view.com.imob.view.panes;

import java.util.List;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.BorderPane;
import view.com.imob.view.panes.ActionPane.ActionCategory;
import view.com.imob.view.parts.Prompt;

/**
 *
 * @author Matheus Michels
 */
public abstract class DefaultPane
    extends
        BorderPane
{
    public static EventType STATE_CHANGED = new EventType( "onStateChanged" );
    
    public abstract void refreshContent();
    public abstract void resize( int height, int width );
    public abstract void initComponents();
    public abstract List<ActionCategory> getActionCategories();
    
    protected void fireActions()
    {
        fireEvent( new Event( STATE_CHANGED ) );
    }
    
    protected void handleException( Exception e )
    {
        Prompt.exception( e );
    }
}
