package com.example.d4_3a04.database;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.MainActivity;
import com.example.d4_3a04.SingleChatProvider.SingleChatManager;

import java.util.List;

public class Cryptosystem extends AppCompatActivity {

    private static DatabaseHelper dbh;

    public static void startDB(Context context){
        if (dbh==null){
            dbh=new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getDBH(){
        return dbh;
    }

    public static void updateEntry(SingleChatManager provider, String employee_id, String other_employee){
        dbh.updateEntry(provider, employee_id, other_employee);
    }

    public static List<String> getNames(String this_employee, String other_employee){
        return dbh.getNames(this_employee, other_employee);
    }

}
