package com.example.d4_3a04;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.AccountManager.LoginPage;
import com.example.d4_3a04.DataTypes.ChatInfo;
import com.example.d4_3a04.SingleChatProvider.MessageListActivity;
import com.example.d4_3a04.SingleChatProvider.SingleChatManager;
import com.example.d4_3a04.database.Cryptosystem;
import com.example.d4_3a04.databinding.SearchForBinding;

public class SearchFor extends AppCompatActivity {
    SearchForBinding binding;

    String employee;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        binding = SearchForBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SearchView sv = (SearchView) findViewById(R.id.searchView);


        this.employee=intent.getStringExtra("this_employee");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d("res", query);
                createConvo(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


    }

    public void createConvo(String other_employee){
        ChatInfo info = new ChatInfo();
        SingleChatManager provider = new SingleChatManager(employee, other_employee, info);

        Cryptosystem.updateEntry(provider, employee, other_employee);
        Cryptosystem.updateEntry(provider, other_employee, employee);

        Intent new_view = new Intent(SearchFor.this, BrowseActiveChats.class);

        new_view.putExtra("Employee_id", employee);
        startActivity(new_view);
    }



}
