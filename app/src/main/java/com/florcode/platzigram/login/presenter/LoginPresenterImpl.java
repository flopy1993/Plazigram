package com.florcode.platzigram.login.presenter;

import android.app.Activity;

import com.florcode.platzigram.login.interactor.LoginInteractor;
import com.florcode.platzigram.login.interactor.LoginInteractorImpl;
import com.florcode.platzigram.login.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor interactor;



    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showPorgressBar();

        interactor.SignIn(username, password, activity, firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goLogin();

        loginView.hideProgressBAr();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgressBAr();
        loginView.loginError(error);

    }
}
