package view.com.imob.view.panes;

import java.util.ArrayList;
import java.util.List;
import view.com.imob.view.panes.ActionPane.ActionCategory;

/**
 *
 * @author Matheus Michels
 */
public class HomePane
    extends
        DefaultPane
{
    @Override
    public void refreshContent()
    {
    }

    @Override
    public List<ActionCategory> getActionCategories()
    {
        return new ArrayList();
    }

    @Override
    public void resize(int height, int width)
    {
        
    }

    @Override
    public void initComponents()
    {
    }
}