package com.florcode.platzigram.login.repository;

import com.florcode.platzigram.login.presenter.LoginPresenter;

public class LoginRepositoryImpl implements LoginRepository {

    private LoginPresenter presenter;

    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void singIn(String username, String password) {
        boolean success = true;

        if(success) {
            presenter.loginSuccess();
        }else{
            presenter.loginError("Error");
        }
    }
}
