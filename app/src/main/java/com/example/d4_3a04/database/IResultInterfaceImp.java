package com.example.d4_3a04.database;

import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.InvalidSQLPacketException;
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.MySQLConnException;
import com.BoardiesITSolutions.AndroidMySQLConnector.Exceptions.MySQLException;
import com.BoardiesITSolutions.AndroidMySQLConnector.ResultSet;
import com.BoardiesITSolutions.AndroidMySQLConnector.IResultInterface;

import java.io.IOException;

public class IResultInterfaceImp implements IResultInterface {

    ResultSet res;

    public ResultSet getResult(){
        return this.res;
    }
    @Override
    public void executionComplete(ResultSet resultSet) {
        this.res = resultSet;
    }

    @Override
    public void handleInvalidSQLPacketException(InvalidSQLPacketException e) {

    }

    @Override
    public void handleMySQLException(MySQLException e) {

    }

    @Override
    public void handleIOException(IOException e) {

    }

    @Override
    public void handleMySQLConnException(MySQLConnException e) {

    }

    @Override
    public void handleException(Exception e) {

    }
}
