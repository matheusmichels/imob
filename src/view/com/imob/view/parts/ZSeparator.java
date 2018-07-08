/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.parts;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Matheus Michels
 */
public class ZSeparator
    extends
        VBox
{
    public ZSeparator( String text )
    {
        Label label = new Label( text );
        label.setStyle( "-fx-font-size: 13pt; -fx-font-weight: bold; -fx-padding: 8px;" );
        
        getChildren().addAll( label );
    }
}