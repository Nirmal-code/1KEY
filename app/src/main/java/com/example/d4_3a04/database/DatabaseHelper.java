package com.example.d4_3a04.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;

import com.example.d4_3a04.SingleChatProvider.SingleChatManager;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteAssetHelper {
    SQLiteDatabase writer = getWritableDatabase();
    SQLiteDatabase reader = getWritableDatabase();



    public DatabaseHelper(Context context) {
        super(context, "providers.db", null, 1);

//        String DROP_TBL = "drop table Providers";
//        writer.execSQL(DROP_TBL );
//
//
//        String CREATE_TBL = "create table  Providers\n"+
//                " (_provider text primary key, " +
//                "this_employee text not null, " +
//                "other_employee  text not null);";
//        writer.execSQL(CREATE_TBL );


    }

    public void addEntry(String this_employee, String other_employee, SingleChatManager provider){
        String INSERT_TBL = "d";
        ContentValues values = new ContentValues(3);
        values.put("this_employee", this_employee);
        values.put("other_employee", other_employee);
        values.put("_provider", SingleChatManager.serializeToJson(provider));


        writer.insert("Providers", null, values);

    }

    public List<String> getNames(){
        List<String> res = new ArrayList<>();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("Providers");



        try {
            Cursor cursor = qb.query(reader, new String[]{"_provider"}, null, null, null, null, null, "30");

            while (cursor.moveToNext()){
                int position = cursor.getColumnIndex("_provider");
                if (position>=0){
                    res.add(cursor.getString(position));
                }
            }
        } catch (Exception e){
            res.add(e.toString());
        }

        return res;
    }
}
