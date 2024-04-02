package com.example.d4_3a04.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.d4_3a04.SingleChatProvider.SingleChatManager;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteAssetHelper implements Serializable {
    SQLiteDatabase writer = getWritableDatabase();
    SQLiteDatabase reader = getWritableDatabase();



    public DatabaseHelper(Context context) {
        super(context, "providers.db", null, 1);
//        createTable();
    }

    public void createTable(){
        String DEL = "drop table Providers";
        writer.execSQL(DEL);
        String CREATE_TBL = "create table  Providers\n"+
                " (_provider text primary key, " +
                "this_employee text not null, " +
                "other_employee  text not null);";
        writer.execSQL(CREATE_TBL );
    }

    public void addEntry(String this_employee, String other_employee, SingleChatManager provider){
        ContentValues values = new ContentValues(3);
        values.put("this_employee", this_employee);
        values.put("other_employee", other_employee);
        String res = SingleChatManager.serializeToJson(provider);
        values.put("_provider", res);


        writer.insert("Providers", null, values);
    }

    public void updateEntry(SingleChatManager provider, String this_employee, String other_employee){
        //On back button, just update the database entry!
        String serialized = SingleChatManager.serializeToJson(provider);
        String UPDATE_TBL = "update  Providers\n"+
                "set _provider="+serialized+
                "where this_employee="+this_employee;
        writer.execSQL(UPDATE_TBL);
    }

    public List<String> getNames(String this_employee, String other_employee){
        List<String> res = new ArrayList<>();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("Providers");



        try {
            Cursor cursor = qb.query(reader, new String[]{"_provider", "this_employee", "other_employee"}, null, null, null, null, null, "30");

            while (cursor.moveToNext()){
                int position = cursor.getColumnIndex("_provider");
                int e1_p = cursor.getColumnIndex("this_employee");
                int e2_p = cursor.getColumnIndex("other_employee");

                if (position>=0){
                    res.add(cursor.getString(position));
                }

//                if (e1_p>=0 && e2_p>=0 && position>=0){
//                    String curr_employee = cursor.getString(e1_p);
//                    String second_employee = cursor.getString(e2_p);
//                    if (curr_employee.equals(this_employee) && second_employee.equals(other_employee)){
//                        res.add(cursor.getString(position));
//                    }
//                }
            }
        } catch (Exception e){
            res.add(e.toString());
        }

        return res;
    }
}
