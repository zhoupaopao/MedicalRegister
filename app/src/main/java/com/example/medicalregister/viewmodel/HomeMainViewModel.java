package com.example.medicalregister.viewmodel;

import com.example.lib.base.BaseViewModel;
import com.example.lib.base.MvvmBaseViewModel;

public class HomeMainViewModel extends MvvmBaseViewModel {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
