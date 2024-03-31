package com.example.d4_3a04.DataTypes;

import java.sql.Time;
import java.util.Date;

public class LogEntity {
    public String employee_id;
    public String message;
    public Date date;
    public Time time;
    public String key;

    public LogEntity(String employee_id, String message, Date date, Time time, String key){
        this.employee_id = employee_id;
        this.message = message;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public String getMessage(){
        return this.message;
    }

    public String getEmployee_id(){
        return this.employee_id;
    }

    public Time getTime(){
        return this.time;
    }
}
