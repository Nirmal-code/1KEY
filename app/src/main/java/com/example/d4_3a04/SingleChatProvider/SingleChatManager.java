package com.example.d4_3a04.SingleChatProvider;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.DataTypes.ChatInfo;
import com.example.d4_3a04.DataTypes.LogEntity;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SingleChatManager implements Serializable {

    String this_employee;
    List<String> other_employees = new ArrayList<>();
    ChatInfo chat_info;

    public SingleChatManager(String this_employee, String other_employee){
        this.this_employee = this_employee;
        this.other_employees.add(other_employee);

        this.chat_info = new ChatInfo(this);
    }

    // Outputs how many logs there are in total.
    public int updateChatLog(String message){
        Date date_time = Calendar.getInstance().getTime();

        LogEntity entity = new LogEntity(this.this_employee, message, date_time, "2921902");
        chat_info.addLog(entity);

        return chat_info.getLog_history().size();

    }

    public void inflate_page_source(AppCompatActivity activity, Context context){
        Intent intent = new Intent(activity, MessageListActivity.class);
        intent.putExtra("Employee_id", this.this_employee);
        intent.putExtra("SCM", this);
        activity.startActivity(intent);
    }


}
