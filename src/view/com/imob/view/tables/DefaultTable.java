package view.com.imob.view.tables;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import view.com.imob.view.parts.Prompt;

/**
 * @author mm
 */
public class DefaultTable<T>
    extends 
        Pagination
{
    private static final int SIZE = 50;
    
    private final ObservableList<T> list = FXCollections.observableArrayList();
    
    public static EventType ON_SELECT = new EventType( "onSelect" );
    public static EventType ON_DOUBLE_CLICK = new EventType( "onDoubleClick" );
    
    public DefaultTable()
    {
        initComponents();
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if ( newSelection != null )
            {
                fireEvent( new Event( ON_SELECT ) );
            }
        });
        
        tableView.setOnMousePressed( new EventHandler<MouseEvent>()
        {
            @Override 
            public void handle(MouseEvent event)
            {
                if ( event.isPrimaryButtonDown() && event.getClickCount() == 2 )
                {
                    fireEvent( new Event( ON_DOUBLE_CLICK ) );
                }
            }
        });
    }
    
    public void setOnClick( EventHandler<MouseEvent> handler )
    {
        tableView.setOnMouseClicked( handler );
    }
    
    public DefaultTable( Column... items )
    {
        initComponents();
        
        setColumns( items );
    }
    
    public Node createPage( int pageIndex )
    {
        int lastIndex;
        int displace = list.size() % SIZE;
        
        if (displace > 0)
        {
            lastIndex = list.size() / SIZE;
        }
        else
        {
            lastIndex = list.size() / SIZE - 1;
        }
        
        int page = pageIndex * SIZE;
        
        for ( int i = page; i < page + SIZE; i++ )
        {
            if (  lastIndex == pageIndex )
            {
                tableView.setItems( FXCollections.observableArrayList( list.subList( pageIndex * SIZE,
                                                                                     Math.min( ( pageIndex * SIZE + displace ), list.size() ) ) ) );
            }
            else
            {
                tableView.setItems( FXCollections.observableArrayList( list.subList( pageIndex * SIZE, 
                                                                                     Math.min( ( pageIndex * SIZE + SIZE ), list.size() ) ) ) );
            }

        }
        return tableView;
    }
    
    public void setColumns( Column ... items )
    {
        if( items != null )
        {
            for ( Column item : items )
            {
                TableColumn column = new TableColumn( item.getLabel() );
                column.setCellValueFactory( new PropertyValueFactory( item.getAttribute() ) );

                if( ! item.getWidth().isNaN() )
                    column.setPrefWidth( item.getWidth() );
                
                if( item.getCallback() != null )
                    column.setCellFactory( item.getCallback() );

                tableView.getColumns().add( column );
            }
        }
    }
   
    public void setItems( List<T> items )
    {
        try
        {
            long max = Math.round( new Double( items.size() ) / SIZE );
            
            setMaxPageIndicatorCount( Math.max( (int) max, 1 ) );
            setPageCount(  Math.max( (int) max, 1 ) ); 
           
            list.setAll( items );
            
            tableView.setItems( list );
            tableView.requestLayout();
        }
        
        catch ( Exception e )
        {
            Prompt.exception( e );
        }
    }
    
    public T getSelectedItem()
    {
        return tableView.getSelectionModel().getSelectedItem();
    }
    
    public void setSelectedItem( T item )
    {
        if( item != null )
        {
            tableView.getSelectionModel().select( item );
        }
    }
    
    public List<T> getSelectedItems()
    {
        return tableView.getSelectionModel().getSelectedItems();
    }
    
    private void initComponents() 
    {
        setPageFactory ( this::createPage );
        
        setStyle( "-fx-page-information-visible: false;" );
        
        getChildren().add( tableView );
    }
    
    private TableView<T> tableView = new TableView();
    
    public static class Column
    {
        private String label = "";
        private String attribute = "";
        private Callback callback;
        private Double width;
        
        public Column( String label, String attribute )
        {
            this( label, attribute, Double.NaN );
        }
        
        public Column( String label, String attribute, Double width )
        {
            this( label, attribute, width, null );
        }
        
        public Column( String label, String attribute, Callback calback )
        {
            this( label, attribute, Double.NaN, calback );
        }
        
        public Column( String label, String attribute, Double width, Callback calback ) 
        {
            this.label = label;
            this.attribute = attribute;
            this.callback = calback;
            this.width = width;
        }

        public String getLabel() 
        {
            return label;
        }

        public String getAttribute() 
        {
            return attribute;
        }

        public Callback getCallback()
        {
            return callback;
        }

        public Double getWidth()
        {
            return width;
        }
    }
}