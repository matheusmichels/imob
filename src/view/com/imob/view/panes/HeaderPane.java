/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.com.imob.model.data.User;
import view.com.imob.view.util.ApplicationUtilities;
import view.com.imob.view.util.ResourceLocator;

/**
 *
 * @author matheus.michels
 */
public class HeaderPane
    extends
        HBox
{
    public HeaderPane()
    {
        initComponents();
    }

    private void initComponents()
    {
//        ImageView systemIcon = new ImageView();
//        systemIcon.setImage( new Image( ResourceLocator.getImage( "logo.png" ) ) );
//        systemIcon.setFitHeight( 30 );
//        systemIcon.setFitWidth( 50 );
//        
//        HBox iconBox = new HBox();
//        iconBox.getChildren().add( systemIcon );
//        
//        iconBox.setAlignment( Pos.TOP_LEFT );
//        
//        ImageView userIcon = new ImageView();
//        userIcon.setImage( new Image( ResourceLocator.getImage( "user.png" ) ) );
//        userIcon.setFitHeight( 30 );
//        userIcon.setFitWidth( 30 );
//        
//        User user = ApplicationUtilities.getInstance().getCurrentUser();
//        
//        if ( user != null )
//        {
//            Label userLabel = new Label( user.getName() );
//        }
//        
//        HBox userBox = new HBox();
//        userBox.getChildren().addAll( userIcon, userLabel );
//        
//        userBox.setAlignment( Pos.TOP_RIGHT );
//        
//        getChildren().addAll( iconBox, userBox );
    }
}
