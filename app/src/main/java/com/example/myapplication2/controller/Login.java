package com.example.myapplication2.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class Login extends AppCompatActivity {
    public String name;
    private FirebaseAuth mAuth;
    private AlertDialog dialog;

    public EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.app_name));
        txtPass = (EditText) findViewById(R.id.TextPassword);
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
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(Login.this, HomePage.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, R.string.faild_login, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    public void onChangeLan(View view) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.app_name));
        final String[] lang = {"English", "עברית"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Login.this);
        myBuilder.setTitle("Choose language");
        myBuilder.setSingleChoiceItems(lang, -1, (dialog, which) -> {
            if (which == 0) {
                setLocal("en");
                recreate();
            } else if (which == 1) {
                setLocal("iw");
                recreate();
            }
            dialog.dismiss();
        });
        myBuilder.create().show();
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View lessonPopupView = getLayoutInflater().inflate(R.layout.password_popup, null);
        EditText mailToSend = (EditText) lessonPopupView.findViewById(R.id.email_send_password_popup);
        Button confirmBtn = (Button) lessonPopupView.findViewById(R.id.confirm_password_popup);
        Button cancelBtn = (Button) lessonPopupView.findViewById(R.id.cancel_password_popup);
        dialogBuilder.setView(lessonPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        confirmBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().sendPasswordResetEmail(mailToSend.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, R.string.password_reset_login, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Login.this, R.string.password_faield_reset_login, Toast.LENGTH_LONG).show();
                }
            });
            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(v -> dialog.dismiss());
    }
}