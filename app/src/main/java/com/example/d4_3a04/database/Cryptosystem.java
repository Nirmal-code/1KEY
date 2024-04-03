package com.example.d4_3a04.database;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import com.BoardiesITSolutions.AndroidMySQLConnector.IResultInterface;
import com.BoardiesITSolutions.AndroidMySQLConnector.ResultSet;
import com.BoardiesITSolutions.AndroidMySQLConnector.Statement;
import com.example.d4_3a04.SingleChatProvider.SingleChatManager;
import com.example.d4_3a04.database.IResultInterfaceImp;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Cryptosystem extends AppCompatActivity {

    private static DatabaseHelper dbh;

    static Connection mysqlConnection;

    public static void startDB(Context context){
        if (mysqlConnection == null){
//            String connectionString = "Server=localhost;Database=PROVIDERS;Uid=root;Pwd=NemoC#1029;";
//            MySqlConnection mysqlConnection = new MySqlConnection(connectionString);
//            mysqlConnection = new Connection("localhost", "root", "NemoC#1029", 3306, "PROVIDERS", new IConnectionInterface());
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try{
                Class.forName("com.mysql.jdbc.Driver");
                mysqlConnection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/testdb?useUnicode=true&characterEncoding=UTF-8", "root", "NemoC#1029");
            }catch (Exception e){
                throw new RuntimeException(e);
            }

//            mysqlConnection.close();
        }
    }

    public static void updateEntry(SingleChatManager provider, String employee_id, String other_employee){
//        mysqlConnection = new Connection("localhost", "root", "NemoC#1029", 3306, "PROVIDERS", new IConnectionInterface());
//        Statement statement = mysqlConnection.createStatement();
//        String serialized = SingleChatManager.serializeToJson(provider);
//
//        String COMMAND = String.format("INSERT INTO providers (this_employee, other_employee, provider) VALUES (\"%s\", \"%s\", \"%s\");", employee_id, other_employee, serialized );
//
//        statement.execute(COMMAND, new IConnectionInterface());
//        mysqlConnection.close();

//        dbh.updateEntry(provider, employee_id, other_employee);
    }

    public static List<String> getNames(String this_employee, String other_employee){
//        mysqlConnection = new Connection("localhost", "root", "NemoC#1029", 3306, "PROVIDERS", new IConnectionInterface());
//
        List<String> output = new ArrayList<>();
//        Statement statement = mysqlConnection.createStatement();
//
//        String COMMAND = String.format("SELECT provider " +
//                                        "FROM PROVIDERS " +
//                                         String.format(" WHERE this_employee=%s AND other_employee=%s;", this_employee, other_employee));
//        IResultInterfaceImp res = new IResultInterfaceImp();
//
//        statement.executeQuery(COMMAND, res);
//
//        ResultSet result = res.getResult();
//
//        if (result!=null){
//            output.add(result.getFields().get(0).toString());
//        }
//        mysqlConnection.close();
        return output;

//        return dbh.getNames(this_employee, other_employee);
    }

}
