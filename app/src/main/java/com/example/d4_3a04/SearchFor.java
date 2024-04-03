package com.example.d4_3a04;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.SingleChatProvider.MessageListActivity;
import com.example.d4_3a04.databinding.SearchForBinding;

public class SearchFor extends AppCompatActivity {
    SearchForBinding binding;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = SearchForBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SearchView sv = (SearchView) findViewById(R.id.searchView);
        sv.getQueryHint();

    }

    public void inflate_page_source(AppCompatActivity activity) {
        Intent intent = new Intent(activity, SearchForBinding.class);
        activity.startActivity(intent);
    }
}
