package com.florcode.platzigram.login.interactor;

import com.florcode.platzigram.login.presenter.LoginPresenter;
import com.florcode.platzigram.login.repository.LoginRepository;
import com.florcode.platzigram.login.repository.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void SignIn(String username, String password) {
        repository.singIn(username, password);
    }
}
