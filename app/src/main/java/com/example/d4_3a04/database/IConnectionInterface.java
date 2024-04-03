package com.example.d4_3a04.database;

import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.InvalidSQLPacketException;
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.MySQLConnException;
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.MySQLException;

import java.io.IOException;

public class IConnectionInterface implements com.BoardiesITSolutions.AndroidMySQLConnector.IConnectionInterface {
    @Override
    public void actionCompleted() {
        //Database switched successfully
    }

    @Override
    public void handleInvalidSQLPacketException(InvalidSQLPacketException e) {
        throw new RuntimeException(e);
    }

    @Override
    public void handleMySQLException(MySQLException e) {
        throw new RuntimeException(e);
    }

    @Override
    public void handleIOException(IOException e) {
        throw new RuntimeException(e);
    }

    @Override
    public void handleMySQLConnException(MySQLConnException e) {
        throw new RuntimeException(e);
    }

    @Override
    public void handleException(Exception e) {
        throw new RuntimeException(e);
    }
}
