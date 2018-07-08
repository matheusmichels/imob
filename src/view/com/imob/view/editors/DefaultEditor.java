package view.com.imob.view.editors;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;
import view.com.imob.view.parts.Prompt;
import view.com.imob.view.util.EditorCallback;

/**
 *
 * @author Matheus Michels
 */
public abstract class DefaultEditor<T>
    extends
        Dialog<T>
{
    public EditorCallback callback;
    public T source;
    
    public DefaultEditor( EditorCallback<T> callback ) 
    {
        this.callback = callback;
        source = callback.getSource();
        
        initComponents();
    }
    
    protected boolean isEmpty( String text )
    {
        if ( text != null && !text.trim().isEmpty() )
        {
            return false;
        }
        
        return true;
    }
    
    private boolean onSave()
    {
        try
        {
            List<String> listError  = new ArrayList();

            validadeInput( listError  );

            if( ! listError .isEmpty() )
            {
                String message = "";

                for( String error : listError )
                {
                    message += "\n   *" + error;
                }

                Prompt.alert( "Alguns campos não estão preenchidos ou estão inválidos", message );
                
                return false;
            }

            else
            {
                obtainInput();
                callback.onEvent();
            }
        }
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
        
        return true;
    }
    
    protected void onCancel()
    {
    };
    
    private void initComponents()
    {
        setTitle( "Editor" );
        setHeaderText( "Editor de Items" );
        setResizable( false );
        
        getDialogPane().getButtonTypes().addAll( cancelBtn, saveBtn );
        
        setOnCloseRequest( new EventHandler() {

            @Override
            public void handle( Event t ) 
            {
                if(  selectedBtn == saveBtn )
                {
                    if ( ! onSave() )
                    {
                        t.consume();
                    }
                }
                
                else
                {
                    onCancel();
                }
            }
        } );

        setResultConverter( new Callback() 
        {
            @Override
            public Object call(  Object p ) 
            {
                return selectedBtn = (ButtonType) p;
            }
        } );
    }
    
    public void open()
    {
        showAndWait();
    }
    
    protected void handleException( Exception e )
    {
        Prompt.exception( e );
    }
    
    protected abstract void validadeInput( List<String> errors ) throws Exception;
    protected abstract void obtainInput();
    protected abstract void setSource( T source );
    
    private ButtonType saveBtn   = new ButtonType( "Salvar",   ButtonData.OK_DONE );
    private ButtonType cancelBtn = new ButtonType( "Cancelar", ButtonData.CANCEL_CLOSE );
    private ButtonType selectedBtn;
}