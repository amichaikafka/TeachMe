package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class OpenScreen extends AppCompatActivity {
   public EditText txtName;
   public String name;
   boolean flag=false;
    private FirebaseAuth mAuth;

    public EditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.open_screen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        txtPass=(EditText)findViewById(R.id.TextPassword);
        String pass=txtPass.getText().toString();

    }

    //TODO need to check how to connecet between email user and user profile
    public void onClickReg(View view) {
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
        Intent intent=new Intent(OpenScreen.this, TeacherOrStudent.class);
        startActivity(intent);
        finish();
    }

    public void onClickLog(View view) {
        Intent intent=new Intent(OpenScreen.this, MainActivity2.class);
        Bundle b=new Bundle();
        txtName=(EditText)findViewById(R.id.txtName);
        name=txtName.getText().toString();
        b.putString("name",name);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void onChangeLan(View view) {

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        final String[] lang={"English","עברית"};
        flag=!flag;
        AlertDialog.Builder myBuilder=new AlertDialog.Builder(OpenScreen.this);
        myBuilder.setTitle("Choose language");
        myBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0) {
                    setLocal("en");

                    recreate();
                }else if(which==1){
                    setLocal("iw");
                    recreate();

                }
               dialog.dismiss();
            }
        });

        myBuilder.create().show();


    }
    public void load(){
        SharedPreferences prefs=getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String lang=prefs.getString("My_lang","");
        setLocal(lang);
    }

    private void setLocal(String lang) {

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