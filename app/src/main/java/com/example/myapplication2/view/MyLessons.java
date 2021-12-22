package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyLessons extends AppCompatActivity {










    //** menu **//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    //TODO: need to add case for every items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==  R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if(item.getItemId() ==  R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if(item.getItemId() ==  R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if(item.getItemId() ==  R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        if (item.getItemId() == R.id.menu_myProfile){startActivity(new Intent(this, TeacherEditProfile.class));}
        return super.onContextItemSelected(item);
    }

}