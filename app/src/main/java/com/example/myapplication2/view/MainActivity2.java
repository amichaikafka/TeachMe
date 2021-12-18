package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication2.R;



 /// class not uses.
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

    //the Menu Bar create
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return super.onCreateOptionsMenu(menu);
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