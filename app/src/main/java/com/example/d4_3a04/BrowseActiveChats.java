package com.example.d4_3a04;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.databinding.SearchForBinding;
import com.example.d4_3a04.DataTypes.ChatInfo;
import com.example.d4_3a04.SingleChatProvider.SingleChatManager;
import com.example.d4_3a04.database.Cryptosystem;
import com.example.d4_3a04.databinding.BrowseActiveChatBinding;

import java.util.List;

public class BrowseActiveChats extends AppCompatActivity {

    private BrowseActiveChatBinding binding;
    String employee_id;
    List<String> other_employee;

    SingleChatManager provider;

    SearchFor searchProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        this.employee_id = intent.getStringExtra("Employee_id");

        // Starting the database connection.
        Cryptosystem.startDB(BrowseActiveChats.this);

        other_employee = Cryptosystem.getOtherEmployee(this.employee_id);

        binding = BrowseActiveChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarActiveChat);

        LinearLayout layout = (LinearLayout) findViewById(R.id.all_chats);


        for (String other_employee: other_employee){
            Log.d("STATUS", other_employee);
            createButton(other_employee, layout);
        }

        BrowseActiveChatBinding.inflate(getLayoutInflater());

        binding.enterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProvider.inflate_page_source(BrowseActiveChats.this);
            }
        });

    }
    public void createButton(String employee_name, LinearLayout layout){
        Button btnTag = new Button(this);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnTag.setText(employee_name);
        btnTag.setTag("enter_convo");

        //add button to the layout
        layout.addView(btnTag);

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the stored provider for given employee

                List<String> res = Cryptosystem.getProvider(employee_id, employee_name);

                // Only creating a new provider if there is no provider for the employee (res.size()=0).
                if (res.size()>0){
                    provider = SingleChatManager.deserializeFromJson(res.get(0));
                    provider.set_employee(employee_id, employee_name);
                }else{
                    ChatInfo chat_info = new ChatInfo();
                    provider = new SingleChatManager(employee_id, employee_name, chat_info);;
                    Cryptosystem.updateEntry(provider,employee_id, employee_name);
                }

                // Open that conversation using given provider.
                provider.inflate_page_source(BrowseActiveChats.this);

                // Disconnect from the database.
                Cryptosystem.disconnectDB();
            }
        });

    }
}
