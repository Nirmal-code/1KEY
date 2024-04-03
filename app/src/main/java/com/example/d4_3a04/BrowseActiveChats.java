package com.example.d4_3a04;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.d4_3a04.DataTypes.ChatInfo;
import com.example.d4_3a04.SingleChatProvider.SingleChatManager;
import com.example.d4_3a04.database.Cryptosystem;
import com.example.d4_3a04.databinding.BrowseActiveChatBinding;

import java.util.List;

public class BrowseActiveChats extends AppCompatActivity {

    private BrowseActiveChatBinding binding;

    SingleChatManager provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cryptosystem.startDB(BrowseActiveChats.this);

        binding = BrowseActiveChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarActiveChat);


        binding.enterConvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> res = Cryptosystem.getNames("Nirmal", "Bob");

                if (res.size()>0){
                    provider = SingleChatManager.deserializeFromJson(res.get(0));
                    Log.d("PExtracted", provider.toString());

                }else{
                    ChatInfo chat_info = new ChatInfo();
                    provider = new SingleChatManager("Nirmal", "Bob", chat_info);;
//                    provider.updateChatLog("Hello World");
                    Log.d("PInitial", provider.toString());
                    Cryptosystem.updateEntry(provider,"Nirmal", "Bob");
                }
                provider.inflate_page_source(BrowseActiveChats.this);
            }
        });
    }
}
