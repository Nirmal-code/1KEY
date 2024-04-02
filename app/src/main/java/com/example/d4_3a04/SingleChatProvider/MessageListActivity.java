package com.example.d4_3a04.SingleChatProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d4_3a04.DataTypes.ChatInfo;
import com.example.d4_3a04.DataTypes.LogEntity;
import com.example.d4_3a04.R;
import com.example.d4_3a04.databinding.ActivityMainBinding;
import com.example.d4_3a04.databinding.SingleChatProviderBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MessageListActivity extends AppCompatActivity {

    private SingleChatManager provider;
    private RecyclerView MessageRecycler;
    private MessageListAdapter MessageAdapter;
    private SingleChatProviderBinding binding;

    private ChatInfo chat_info;


    private String employee_id;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = SingleChatProviderBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();

        this.employee_id = intent.getStringExtra("Employee_id");

        // Single Chat Manager has idea of which employee is main, and secondary ones.
        this.provider = (SingleChatManager) getIntent().getSerializableExtra("SCM");
        this.chat_info = provider.chat_info;


        this.MessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        this.MessageAdapter = new MessageListAdapter(this, chat_info, this.employee_id);

        this.MessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        this.MessageRecycler.setAdapter(this.MessageAdapter);

        loadChat();


        binding.buttonGchatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText t = (EditText) findViewById(R.id.edit_gchat_message);

                int size = provider.updateChatLog(t.getText().toString());

                MessageAdapter.onBindViewHolder(MessageAdapter.onCreateViewHolder(findViewById(R.id.recycler_gchat), 1), size-1);

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(MessageRecycler.getWindowToken(), 0);

                t.getText().clear();
            }
        });

    }

    public void loadChat(){
        for (int i=0; i<this.chat_info.getLog_history().size(); i++){
            MessageAdapter.onBindViewHolder(MessageAdapter.onCreateViewHolder(findViewById(R.id.recycler_gchat), 1), i);
        }

    }




}
