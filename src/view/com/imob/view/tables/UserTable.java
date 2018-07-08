package view.com.imob.view.tables;

import model.com.imob.model.data.User;

/**
 *
 * @author Matheus Michels
 */
public class UserTable
    extends
        DefaultTable<User>
{

    public UserTable()
    {
        setColumns
        (
            new DefaultTable.Column( "#", "id", 40d ),
            new DefaultTable.Column( "Nome", "name", 240d ),
            new DefaultTable.Column( "Login", "login", 202d ),
            new DefaultTable.Column( "Email", "email", 300d )
        );
    }
}
