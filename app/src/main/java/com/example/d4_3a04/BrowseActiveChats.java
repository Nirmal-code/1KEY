package com.example.d4_3a04;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    String other_employee;

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

        // For testing purposes. Will eventually be able to make requests.
        if (other_employee.equals("")){
            binding.enterConvo.setText("Bob");
            other_employee = "Bob";
        }else {
            binding.enterConvo.setText(other_employee);
        }


        binding.enterConvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the stored provider for given employee

                List<String> res = Cryptosystem.getProvider(employee_id, other_employee);

                // Only creating a new provider if there is no provider for the employee (res.size()=0).
                if (res.size()>0){
                    provider = SingleChatManager.deserializeFromJson(res.get(0));
                    provider.set_employee(employee_id, other_employee);
                }else{
                    ChatInfo chat_info = new ChatInfo();
                    provider = new SingleChatManager(employee_id, other_employee, chat_info);;
                    Cryptosystem.updateEntry(provider,employee_id, other_employee);
                }

                // Open that conversation using given provider.
                provider.inflate_page_source(BrowseActiveChats.this);

                // Disconnect from the database.
                Cryptosystem.disconnectDB();
            }
        });

        binding.enterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProvider.inflate_page_source(BrowseActiveChats.this);
            }
        });
    }
}
