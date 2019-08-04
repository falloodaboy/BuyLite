package com.pittchallenge.buylite;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class LifeCycleObserver implements LifecycleObserver {
    private final static String TAG = "Observer";


        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void printMessage(){
            Log.d(TAG, "printMessage: Message created in Observer Class.");
        }
}
