package com.niu.protect.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.niu.protect.repository.MainRepository;
public class MainViewModelFactory implements ViewModelProvider.Factory {
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(MainRepository.getInstance());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
