package view.com.imob.view.util;

/**
 *
 * @author Matheus Michels
 */
public abstract class EditorCallback<T> 
    implements 
        EventListener
{
    private T source;

    public EditorCallback( T source ) 
    {
        this.source = source;
    }
    
    public T getSource()
    {
        return source;
    }
}
