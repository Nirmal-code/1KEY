package com.example.d4_3a04;

import android.os.Bundle;

import com.example.d4_3a04.DataTypes.ChatInfo;
import com.example.d4_3a04.DataTypes.LogEntity;
import com.example.d4_3a04.SingleChatProvider.SingleChatManager;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.d4_3a04.database.DatabaseHelper;
import com.example.d4_3a04.databinding.ActivityMainBinding;



import android.view.Menu;
import android.view.MenuItem;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        SingleChatManager provider = new SingleChatManager("Nirmal", "Bob");

        DatabaseHelper dbh = new DatabaseHelper(this);
        List<String> res = dbh.getNames();

        if (res.size()>0){
            provider = SingleChatManager.deserializeFromJson(res.get(0));
            ChatInfo data = provider.chat_info;
            for (LogEntity instance: data.getLog_history()){
                Log.d("LOGG", instance.message);
            }
        }else{
            dbh.addEntry("Nirmal", "Bob", provider);
        }


        SingleChatManager finalProvider = provider;
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalProvider.inflate_page_source(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}