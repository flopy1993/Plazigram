package com.florcode.platzigram.login.presenter;

import com.florcode.platzigram.login.interactor.LoginInteractor;
import com.florcode.platzigram.login.interactor.LoginInteractorImpl;
import com.florcode.platzigram.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password) {
        loginView.disableInputs();
        loginView.showPorgressBar();

        interactor.SignIn(username, password);
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
