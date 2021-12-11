package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication2.R;

public class Setting extends AppCompatActivity {

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
            case R.id.menu_home:
                intent = new Intent(Setting.this, HomePage.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_myLesson:
                intent = new Intent(Setting.this, MyLessons.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_contact:
                intent = new Intent(Setting.this, ContactUs.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();
        }

        return super.onContextItemSelected(item);
    }
}