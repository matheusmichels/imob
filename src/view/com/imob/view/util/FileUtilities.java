/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.util;

import java.awt.Desktop;
import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author Matheus Michels
 */
public class FileUtilities
{
    public static void open( final File file ) throws Exception
    {
        validadeFile( file );

        new Thread( new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    if( Desktop.isDesktopSupported() )
                        Desktop.getDesktop().open( file );
                } 
                catch ( Exception e )
                {
                    ApplicationUtilities.handleException( e );
                }
            }
        } ).start();
    }
    
    private static void validadeFile( File file ) throws Exception
    {
        if( ! file.exists() )
            throw new Exception( "File not found: " + file.getAbsolutePath() );
        
        if( ! file.canRead() )
            throw new Exception( "File can not read: " + file.getAbsolutePath() );
        
        if( ! file.canWrite() )
            throw new Exception( "File can not writer: " + file.getAbsolutePath() );
    }
    
    public static File saveFile( String title, String fileName )
    {
        FileChooser  fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "Report Files", ".pdf" ) );

        fileChooser.setTitle( title );
        fileChooser.setInitialDirectory( new File( System.getProperty( "user.home" ) ) );         
        fileChooser.setInitialFileName( fileName );

        return fileChooser.showSaveDialog( ApplicationUtilities.getInstance().getWindow() );
    }
}