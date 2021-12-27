package com.example.myapplication2.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

public class AboutUs extends AppCompatActivity {
    private boolean isTeacher;
    Bundle userToMove=new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        savedInstanceState = getIntent().getExtras();
        isTeacher = savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);
    }






    //** menu **//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        MenuItem menuItem=menu.getItem(1);
        if(!isTeacher){
            menuItem.setVisible(false);
        }
        return true;
    }

    //TODO: need to add case for every items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) startActivity(new Intent(this, HomePage.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_myLesson) startActivity(new Intent(this, MyLessons.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_setting) startActivity(new Intent(this, Settings.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        if (item.getItemId() == R.id.menu_myProfile){
            if(!isTeacher){
                Toast.makeText(this, "only teacher can edit profile", Toast.LENGTH_LONG).show();
            }else {
                startActivity(new Intent(this, TeacherEditProfile.class).putExtras(userToMove));
            }
        }
        return super.onContextItemSelected(item);
    }
}


