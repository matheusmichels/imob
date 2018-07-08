/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.panes;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import view.com.imob.view.util.ApplicationUtilities;

/**
 *
 * @author matheus.michels
 */
public class FooterPane
    extends
        HBox
{
    public FooterPane()
    {
        initComponents();
    }

    private void initComponents()
    {
        getChildren().add( new Label( ApplicationUtilities.getInstance().getCurrentUser().getName() ) );
    }
}
