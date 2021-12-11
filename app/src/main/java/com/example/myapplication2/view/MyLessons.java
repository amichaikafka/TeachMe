package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication2.R;

public class MyLessons extends AppCompatActivity {

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
                intent = new Intent(MyLessons.this, HomePage.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_setting:
                intent = new Intent(MyLessons.this, Setting.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_contact:
                intent = new Intent(MyLessons.this, ContactUs.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();
        }


        return super.onContextItemSelected(item);
    }


    public void onClickBack(View view) {
        Intent intent=new Intent(MyLessons.this, OpenScreen.class);
        Bundle b=new Bundle();
        b.putInt("R",1);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}