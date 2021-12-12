package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication2.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //TODO: need to add case for every items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        Bundle b;
        switch(item.getItemId()) {

            case R.id.menu_myLesson:
                intent = new Intent(HomePage.this, MyLessons.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_setting:
                intent = new Intent(HomePage.this, Settings.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_contact:
                intent = new Intent(HomePage.this, ContactUs.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();
        }

        return super.onContextItemSelected(item);
    }


    public void onClickSearchForTeacher(View view) {
        Intent intent = new Intent(HomePage.this, HomePageNext.class);
        Bundle b = new Bundle();
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}