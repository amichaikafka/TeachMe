package com.example.myapplication2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class OpenScreen extends AppCompatActivity {
   public EditText txtName;
   public String name;
   boolean flag=false;

    public EditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load();
        setContentView(R.layout.open_screen);
//        txtName=(EditText)findViewById(R.id.txtName);
//        name=txtName.getText().toString();
//        System.out.println("____________"+name);
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.app_name));
        txtPass=(EditText)findViewById(R.id.TextPassword);
        String pass=txtPass.getText().toString();

    }

    public void onClickReg(View view) {
        Intent intent=new Intent(OpenScreen.this,TeacherOrStufent.class);
        startActivity(intent);
        finish();
    }

    public void onClickLog(View view) {
        Intent intent=new Intent(OpenScreen.this,MainActivity2.class);
        Bundle b=new Bundle();
        txtName=(EditText)findViewById(R.id.txtName);
        name=txtName.getText().toString();
        b.putString("name",name);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void onChangeLan(View view) {
        final String[] lang={"English","עברית"};
        flag=!flag;
        System.out.println("asfffffffffffffffffffffffffffffffffffffff");
        AlertDialog.Builder myBuilder=new AlertDialog.Builder(OpenScreen.this);
        myBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0) {
                    setLocal("iw");
                    recreate();
                }if(which==1){
                    setLocal("iw");
                    recreate();

                }
            }
        });


    }
    public void load(){
        SharedPreferences prefs=getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String lang=prefs.getString("My_lang","");
        setLocal(lang);
    }

    private void setLocal(String lang) {
        flag=!flag;
        if(flag) {
            Locale l = new Locale(lang);
            Locale.setDefault(l);
            Configuration config=new Configuration();
            config.locale=l;
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
            SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
            editor.putString("My_lang",lang);
            editor.apply();
        }
    }
}