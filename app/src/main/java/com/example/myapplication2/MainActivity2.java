package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    public String name;
    public String txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        savedInstanceState=getIntent().getExtras();
        txtName=savedInstanceState.getString("name");

        txtName="hello  "+txtName;

        TextView txt=findViewById(R.id.textView3);
        txt.setText(txtName);



    }

    public void onClickBack(View view) {
        Intent intent=new Intent(MainActivity2.this, OpenScreen.class);
        Bundle b=new Bundle();
        b.putInt("R",1);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}