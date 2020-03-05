package com.example.myapplication.Classes;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class AsyncFetchNameOnTitle extends AsyncTask<String,Integer,String>  {

    private WeakReference<TextView> text_view;
    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
