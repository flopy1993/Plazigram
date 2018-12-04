package com.florcode.platzigram.login.interactor;

import android.app.Activity;

import com.florcode.platzigram.login.presenter.LoginPresenter;
import com.florcode.platzigram.login.repository.LoginRepository;
import com.florcode.platzigram.login.repository.LoginRepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void SignIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.singIn(username, password, activity, firebaseAuth);
    }
}
