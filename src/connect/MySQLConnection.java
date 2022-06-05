package connect;

import interfaces.IDBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection implements IDBConnection
{
    private String server = null;
    private String database = null;
    private String username = null;
    private String password = null;
    public MySQLConnection( String server, String database, String username, String password )
    {
        this.server = server;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    @Override
    public Connection getConnection()
    {
        Connection connection = null;
        try
        {
            Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
            DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
            connection = DriverManager.getConnection( getConnectionString() );
        }
        catch( ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e )
        {
            e.printStackTrace();
        }
        return( connection );
    }
    private String getConnectionString()
    {
        return( "jdbc:mysql://" + this.server + "/" + this.database + "?user=" + this.username + "&password=" + this.password );
    }
}