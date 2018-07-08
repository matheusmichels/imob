package view.com.imob.view.util;

import javafx.scene.control.TextField;

/**
 *
 * @author Matheus Michels
 */
public class IntegerField
    extends
        TextField
{
    public IntegerField()
    {
        super();
    }
    
    public IntegerField( String text )
    {
        super( text );
    }
    
    @Override
    public void replaceText( int start, int end, String text )
    {
        if ( validate( text ) )
        {
            super.replaceText( start, end, text );
        }
    }

    @Override
    public void replaceSelection( String text )
    {
        if ( validate( text ) )
        {
            super.replaceSelection( text );
        }
    }

    private boolean validate( String text )
    {
        return text.matches("[0-9]*");
    }
    
    public void setValue( Number number )
    {
        setText( String.valueOf( number ) );
    }
}