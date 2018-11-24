package com.florcode.platzigram.login.view;

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showPorgressBar();
    void hideProgressBAr();

    void loginError(String error);

    void goCreateAccount();
    void goLogin();
}
