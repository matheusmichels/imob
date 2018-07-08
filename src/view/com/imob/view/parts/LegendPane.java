/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.parts;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author Matheus Michels
 */
public class LegendPane
    extends HBox
{
    public static final int TYPE_SALE = 0;
    public static final int TYPE_PAYMENT = 1;
    
    private int type = 0;
    
    public LegendPane()
    {
        this( 0 );
    }
    
    public LegendPane( int type )
    {
        this.type = type;
        
        initComponents();
    }
    
    private void initComponents()
    {
        setAlignment( Pos.CENTER );
        
        ImageView imgNoPaid = new ImageView( ResourceLocator.getImage( type == TYPE_SALE ? "nopaid.png" : "delete.png" ) );
        imgNoPaid.setFitHeight( 50 );
        imgNoPaid.setFitWidth( 50 );
        
        ImageView imgPaid = new ImageView( ResourceLocator.getImage( type == TYPE_SALE ? "paid.png" : "money.png" ) );
        imgPaid.setFitHeight( 50 );
        imgPaid.setFitWidth( 50 );
        
        String style = "-fx-padding: 15; -fx-font-style: italic; -fx-font-size: 16;";
        
        Label lbNoPaid = new Label( type == TYPE_SALE ? " - Não Quitado" : " - Não Pago" );
        lbNoPaid.setStyle( style );
        
        Label lbPaid = new Label( type == TYPE_SALE ? " - Quitado" : " - Pago" );
        lbPaid.setStyle( style );
        
        getChildren().addAll( imgPaid, lbPaid, imgNoPaid, lbNoPaid );
    }
}
