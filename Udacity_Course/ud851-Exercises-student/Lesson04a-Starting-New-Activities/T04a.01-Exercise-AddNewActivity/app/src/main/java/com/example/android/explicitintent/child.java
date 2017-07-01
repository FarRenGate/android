package com.example.android.explicitintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class child extends AppCompatActivity {
    TextView mTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTextMessage = (TextView) findViewById(R.id.tv_display);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

    }
}
