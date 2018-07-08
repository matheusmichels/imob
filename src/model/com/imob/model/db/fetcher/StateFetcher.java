package model.com.imob.model.db.fetcher;

import java.sql.ResultSet;
import model.com.imob.model.data.State;

/**
 *
 * @author Matheus Michels
 */
public class StateFetcher
    implements
        Fetcher<State>
{
    @Override
    public State fetch( ResultSet resultSet ) throws Exception
    {
        int i = 1;
        
        State state = new State();
        
        state.setId( resultSet.getInt( i++ ) );
        state.setName( resultSet.getString( i++ ) );
        state.setUf( resultSet.getString( i++ ) );
        
        return state;
    }
}
