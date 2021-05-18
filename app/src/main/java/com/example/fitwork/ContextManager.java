package com.example.fitwork;

import android.app.Application;
import android.content.Context;

public class ContextManager extends Application {

    private static ContextManager contextManager = null;


    public static ContextManager getInstance(){

        if(contextManager == null)
            contextManager = new ContextManager();
        return contextManager;
    }

    public static Context getContext(){
        return getInstance().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contextManager = this;
    }
}
