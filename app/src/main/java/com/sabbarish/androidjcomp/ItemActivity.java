package com.sabbarish.androidjcomp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sabbarish.androidjcomp.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        String type = getIntent().getStringExtra("type");
        Toast.makeText(this,""+type,Toast.LENGTH_SHORT).show();
    }
}