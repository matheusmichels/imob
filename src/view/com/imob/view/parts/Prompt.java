package view.com.imob.view.parts;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author matheus.michels
 */
public class Prompt 
{
    public static void info( String content )
    {
        info( null, content );
    }
    
    public static void info ( String title, String content )
    {
        createAlert( title, content, AlertType.INFORMATION );
    }
    
    public static void error( String content )
    {
        error( null, content );
    }
    
    public static void error ( String title, String content )
    {            
        createAlert( title, content, AlertType.ERROR );
    }
    
    public static void alert( String content )
    {
        alert( null, content );
    }
    
    public static void alert( String title, String content )
    {
        createAlert( title, content, AlertType.WARNING );
    }
    
    public static boolean confirm( String message )
    {
        return confirm( null, message );
    }
    
    public static boolean confirm( String title, String message )
    {
        ButtonType btnYes = new ButtonType( "Sim", ButtonData.YES );
        ButtonType btnNo = new ButtonType( "Não", ButtonData.NO );
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
        
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add( new Image( ResourceLocator.getImage( "confirm.png" ) ) );
        
        alert.getDialogPane().setPrefSize( 400,  200 );
        alert.setTitle( "Confirmação" );
        alert.setHeaderText( title == null ? "Você tem certeza ?" : title );
        alert.setContentText( message );
        alert.getButtonTypes().setAll( btnYes, btnNo );
        
        ((Button) alert.getDialogPane().lookupButton( btnYes )).setDefaultButton( false );
        ((Button) alert.getDialogPane().lookupButton( btnNo )).setDefaultButton( true );
        
        alert.showAndWait();
        
        return alert.getResult() == btnYes;
    }
    
    public static String input ( String message, String content )
    {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.getDialogPane().setPrefSize( 400,  200 );
        inputDialog.setTitle( "Entrada de Dados" );
        inputDialog.setHeaderText( message );
        inputDialog.setContentText( content );
        inputDialog.getDialogPane().getButtonTypes().remove( ButtonType.CANCEL );
        inputDialog.showAndWait();
        
        return inputDialog.getResult();
    }
    
    public static void exception( Exception e )
    {
        e.printStackTrace();
        
        Alert alert = new Alert( AlertType.ERROR );
        alert.setTitle( "Exceção" );
        alert.setHeaderText( "Look, an Exception Dialog" );

        StringWriter sw = new StringWriter();
        e.printStackTrace( new PrintWriter( sw ) );

        TextArea textarea = new TextArea( sw.toString() );
        textarea.setEditable(false);
        textarea.setWrapText(true);

        textarea.setMaxWidth( Double.MAX_VALUE );
        textarea.setMaxHeight( Double.MAX_VALUE );
        
        GridPane.setVgrow( textarea, Priority.ALWAYS );
        GridPane.setHgrow( textarea, Priority.ALWAYS );

        GridPane expContent = new GridPane();
        expContent.setMaxWidth( Double.MAX_VALUE );
        expContent.add( new Label( "The exception stacktrace was:" ), 0, 0 );
        expContent.add( textarea, 0, 1 );

        alert.getDialogPane().setExpandableContent( expContent );
        alert.getDialogPane().setExpanded( true );

        alert.showAndWait();
    }
    
    private static Alert createAlert( String title, String content, AlertType type )
    {
        if ( title == null )
        {
            title = type == AlertType.INFORMATION  ? "Informação"  :
                    type == AlertType.WARNING      ? "Aviso"       :
                    type == AlertType.ERROR        ? "Erro"        :
                                                     "[Indefinido]";
        }
        
        String icon = type == AlertType.INFORMATION ? "info.png"    :
                      type == AlertType.WARNING     ? "warning.png" :
                      type == AlertType.ERROR       ? "error.png"   :
                                                      null;
        
        Alert alert = new Alert( type );
        
        if ( icon != null )
        {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add( new Image( ResourceLocator.getImage( icon ) ) );
        }
        
        alert.getDialogPane().setPrefSize( 400,  200 );
        alert.setTitle( title );
        alert.setHeaderText( title );
        alert.setContentText( content );
        alert.showAndWait();
        
        return alert;
    }
}