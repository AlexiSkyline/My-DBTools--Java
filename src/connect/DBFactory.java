package connect;

import enums.DBType;
import interfaces.IDBConnection;

public class DBFactory
{
    public static IDBConnection getConnection( DBType type, String server, String database, String username, String password )
    {
        IDBConnection connection = null;
        switch( type )
        {
            case MySQL:
                connection = new MySQLConnection( server, database, username, password );
                break;
            case MSSQLSERVER:
                connection = new SQLServerConnection( server, database, username, password );
        }
        return( connection );
    }
}