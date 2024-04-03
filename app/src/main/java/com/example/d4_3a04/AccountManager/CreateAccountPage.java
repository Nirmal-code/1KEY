package com.example.d4_3a04.AccountManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.BrowseActiveChats;
import com.example.d4_3a04.databinding.CreateAccountPageProviderBinding;

public class CreateAccountPage extends AppCompatActivity {
    private CreateAccountPageProviderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = com.example.d4_3a04.databinding.CreateAccountPageProviderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.headerToolbar);

        Button create_account_button = binding.submitButton;
        TextView back_link = binding.backLink;

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company_email = binding.companyEmailInput.getText().toString().trim();
                String password = binding.passwordInput.getText().toString().trim();

                // TODO: validate company email and password for account creation

                // navigate to BrowseActiveChats page
                Intent new_view = new Intent(CreateAccountPage.this, BrowseActiveChats.class);
                startActivity(new_view);
            }
        });

        back_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
