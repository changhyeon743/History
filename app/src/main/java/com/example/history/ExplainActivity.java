package com.example.history;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExplainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        Intent intent = getIntent();
        textView = findViewById(R.id.textView);
        textView.setText(intent.getStringExtra("explain"));
    }
}
