package com.florcode.platzigram;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.florcode.platzigram.view.ContainerActivity;
import com.florcode.platzigram.view.CreateAccountActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goPlazti(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.platzi.com"));
        startActivity(intent);
    }

    public void goCreateAccount(View view){

        startActivity( new Intent(LoginActivity.this, CreateAccountActivity.class));
    }

    public void goLogin(View view){
        Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
        startActivity(intent);
    }
}
