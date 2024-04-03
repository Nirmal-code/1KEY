package com.example.d4_3a04;

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

    SingleChatManager provider;

    SearchFor searchProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starting the database connection.
        Cryptosystem.startDB(BrowseActiveChats.this);

        binding = BrowseActiveChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarActiveChat);


        binding.enterConvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the stored provider for given employee
                List<String> res = Cryptosystem.getProvider("Nirmal", "Bob");

                // Only creating a new provider if there is no provider for the employee (res.size()=0).
                if (res.size()>0){
                    provider = SingleChatManager.deserializeFromJson(res.get(0));
                }else{
                    ChatInfo chat_info = new ChatInfo();
                    provider = new SingleChatManager("Nirmal", "Bob", chat_info);;
                    Cryptosystem.updateEntry(provider,"Nirmal", "Bob");
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
