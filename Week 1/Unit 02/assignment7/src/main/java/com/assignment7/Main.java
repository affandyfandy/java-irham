package com.assignment7;

import com.assignment7.managers.AppManager;

public class Main {
    public static void main(String[] args) {
        AppManager manager = AppManager.INSTANCE;
        manager.start();
    }
}