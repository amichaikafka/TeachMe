package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class Settings extends AppCompatActivity {

    boolean flag=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(findViewById(R.id.fragment_container) != null){

            if(savedInstanceState != null){
                return;
            }

            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        }
    }




        //** menu **//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    //TODO: need to add case for every items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if (item.getItemId() == R.id.menu_myLesson) startActivity(new Intent(this, MyLessons.class));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, OpenScreen.class));
        }
        return super.onContextItemSelected(item);
    }
}