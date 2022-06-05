package interfaces;

import java.sql.Connection;

public interface IDBConnection
{
    Connection getConnection();
}