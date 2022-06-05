package connect;

import java.util.List;
import java.util.Vector;

public class Row
{
    private List<Column> columns = null;
    public Row()
    {
        this.columns = new Vector<Column>();
    }
    public void add( Column column )
    {
        this.columns.add( column );
    }
    public Column get( int index )
    {
        return( this.columns.get( index ) );
    }
    public int getColumnCount()
    {
        return( this.columns.size() );
    }
    public List<Column> getColumns()
    {
        return( this.columns );
    }
}