package connect;

import enums.DBType;
import enums.QueryType;
import interfaces.IDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class DBConnection
{
    private Connection connection = null;
    private Statement statement = null;
    private IDBConnection dbconnection = null;
    private DBType type = null;
    private String server = null;
    private String database = null;
    private String username = null;
    private String password = null;
    public DBConnection( DBType type, String server, String database, String username, String password )
    {
        this.type = type;
        this.server = server;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    public void open()
    {
        try
        {
            if( this.connection == null || this.connection.isClosed() )
            {
                this.dbconnection = DBFactory.getConnection( type, server, database, username, password );
                this.connection = this.dbconnection.getConnection();
                this.statement = this.connection.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
    public List<Row> execute( String query, QueryType type )
    {
        List<Row> rows = new Vector<Row>();
        try
        {
            ResultSet rs = null;
            ResultSetMetaData rm = null;
            if( type == QueryType.READ )
            {
                rs = this.statement.executeQuery( query );
                if( rs.next() )
                {
                    rs.beforeFirst();
                    rm = rs.getMetaData();
                    while( rs.next() )
                    {
                        Row row = new Row();
                        for( int i = 0; i < rm.getColumnCount(); i++ )
                        {
                            row.add( new Column( rs.getObject( i + 1 ) ) );
                        }
                        rows.add( row );
                    }
                }
            }
            else
            {
                this.statement.execute( query );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        return( rows );
    }
    public Object[][] RowListToMatrix( String query )
    {
        Object[][] matrix = null;
        List<Row> rows = execute( query, QueryType.READ );
        if( rows.size() > 0 )
        {
            matrix = new Object[rows.size()][];
            for( int i = 0; i < matrix.length; i++ )
            {
                matrix[i] = new Object[rows.get( i ).getColumnCount()];
                for( int j = 0; j < matrix[i].length; j++ )
                {
                    matrix[i][j] = rows.get( i ).getColumns().get( j ).getValue();
                }
            }
        }
        return( matrix );
    }
    public void close()
    {
        try
        {
            this.connection.close();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
}