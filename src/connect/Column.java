package connect;

public class Column
{
    private Object value = null;
    public Column( Object value )
    {
        this.value = value;
    }
    public Object getValue()
    {
        return( this.value );
    }
}