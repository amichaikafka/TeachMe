package com.example.myapplication2.controller;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.Comment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Locale;

public class Login extends AppCompatActivity {
    public EditText txtName;
    public String name;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    DatabaseReference myRef = null;
    Bundle userToMove = new Bundle();
    private FirebaseUser mUser;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        txtPass = (EditText) findViewById(R.id.TextPassword);
        String pass = txtPass.getText().toString();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void onClickReg(View view) {
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
        finish();
    }

    public void onClickLog(View view) {
        EditText myEmail = findViewById(R.id.email_sign_in);
        EditText myPassword = findViewById(R.id.TextPassword);
        if (!myEmail.getText().toString().isEmpty() && !myPassword.getText().toString().isEmpty()) {

            mAuth.signInWithEmailAndPassword(myEmail.getText().toString(), myPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(Login.this, HomePage.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "failed :(", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

    }

    public void onChangeLan(View view) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        final String[] lang = {"English", "עברית"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Login.this);
        myBuilder.setTitle("Choose language");
        myBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocal("en");

                    recreate();
                } else if (which == 1) {
                    setLocal("iw");
                    recreate();

                }
                dialog.dismiss();
            }
        });

        myBuilder.create().show();


    }

    public void load() {
        SharedPreferences prefs = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String lang = prefs.getString("My_lang", "");
        setLocal(lang);
    }

    private void setLocal(String lang) {

        Locale l = new Locale(lang);
        Locale.setDefault(l);
        Configuration config = new Configuration();
        config.locale = l;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    public void onForgotPassword(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View lessonPopupView = getLayoutInflater().inflate(R.layout.password_popup, null);
        EditText mailToSend = (EditText) lessonPopupView.findViewById(R.id.email_send_password_popup);
        Button confrimBtn = (Button) lessonPopupView.findViewById(R.id.confirm_password_popup);
        Button cancelBtn = (Button) lessonPopupView.findViewById(R.id.cancel_password_popup);
        dialogBuilder.setView(lessonPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        confrimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(mailToSend.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Password reset sent to email",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Login.this, "faild to reset sent to email",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}