package com.example.d4_3a04.DataTypes;

import java.util.ArrayList;
import java.util.List;
import com.example.d4_3a04.SingleChatProvider.SingleChatProvider;

public class ChatInfo implements InfoEntity{
    private List<String> employee_ids;
    private List<LogEntity> log_history;
    private SingleChatProvider provider;


    public ChatInfo(SingleChatProvider new_provider){
        this.provider = new_provider;
        this.employee_ids = new ArrayList<>();
        this.log_history = new ArrayList<LogEntity>();
    }

    @Override
    public InfoType getInfoType() {
        return InfoType.Log;
    }

    public List<String> getEmployee_ids(){
        return this.employee_ids;
    }

    public SingleChatProvider getProvider(){
        return this.provider;
    }

    public List<LogEntity> getLog_history(){
        return this.log_history;
    }

    public void addLog(LogEntity entity){
        log_history.add(entity);
    }

    public void addUser(String UUID){
        employee_ids.add(UUID);
    }

}
