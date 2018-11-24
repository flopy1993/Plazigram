package com.florcode.platzigram.login.view;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.florcode.platzigram.R;
import com.florcode.platzigram.login.presenter.LoginPresenter;
import com.florcode.platzigram.login.presenter.LoginPresenterImpl;
import com.florcode.platzigram.view.ContainerActivity;
import com.florcode.platzigram.view.CreateAccountActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {



    private TextInputEditText username, password;
    private Button login;
    private ProgressBar progressBarLogin;
    private LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressbarLogin);
        hideProgressBAr();

        presenter = new LoginPresenterImpl(this);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("") || password.getText().toString()
                        .equals("")){
                    loginError("Ingresa tus datos");
                }

                presenter.signIn(username.getText().toString(), password.getText().toString());
            }
        });

    }


    public void goPlazti(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.platzi.com"));
        startActivity(intent);
    }


    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showPorgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBAr() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, getString(R.string.login_error) + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goCreateAccount() {
            startActivity( new Intent(LoginActivity.this, CreateAccountActivity.class));
    }

    @Override
    public void goLogin() {
            Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
            startActivity(intent);
       }
}

