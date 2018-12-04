package com.florcode.platzigram.login.view;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;


import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.florcode.platzigram.R;
import com.florcode.platzigram.login.presenter.LoginPresenterImpl;
import com.florcode.platzigram.view.ContainerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;



public class LoginActivity extends AppCompatActivity implements LoginView {

    private TextInputEditText username, password;
    private Button login;

    private LoginButton loginFacebook;

    private ProgressBar progressBarLogin;
    private LoginPresenterImpl presenter;

    private static final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);



        callbackManager = CallbackManager.Factory.create();

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.w(TAG, "Usuario Logueado" + firebaseUser.getEmail());

                }else{
                    Log.w(TAG, "Usuario NO Logueado" );
                }
            }
        };

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        loginFacebook = (LoginButton) findViewById(R.id.login_facebook);
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
                singIn(username.getText().toString(), password.getText().toString());

            }
        });

        loginFacebook.setReadPermissions("email", "public_profile");

        loginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG, "Sucess" + loginResult.getAccessToken().getApplicationId());
                singInFacebookFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "Error" + error.toString());
                error.printStackTrace();
            }
        });
    }

//    private String keyHashes() {
//        PackageInfo packageInfo;
//        String KeyHas = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo("com.florcode.platzigram", PackageManager.GET_SIGNATURES);
//            for(Signature signature : packageInfo.signatures){
//                MessageDigest messageDigest;
//                messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                KeyHas = new String(Base64.encode(messageDigest.digest(),0));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return KeyHas;
//    }

    private void singInFacebookFirebase(AccessToken accessToken) {
        try {
            //Log.w("Token", "Token " + accessToken.getToken());
            AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
            // Log.w("Token", "Token " + authCredential.getProvider() + " AAA " + authCredential.getSignInMethod());
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.w("Task", "Token " + task);
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        SharedPreferences preferences =
                                getSharedPreferences("USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", user.getEmail());
                        editor.commit();
                        goLogin();
                        Toast.makeText(LoginActivity.this, "Login Facebook", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.w(TAG, "Incorrecto " + task.getException());
                        Toast.makeText(LoginActivity.this, "Login NO Facebook", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Log.w("Exception", "Token " + e.toString());
        }
    }

    private void singIn(String username, String password) {
        presenter.signIn(username, password, this, firebaseAuth);
    }

    public void goCreateAccount(View view){
        goCreateAccount();
    }

    public void goPlazti(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.platzi.com"));
        startActivity(intent);
    }
    public void goLogin(View view) {
        goLogin();
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

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);


    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}

