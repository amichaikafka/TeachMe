package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.api.Lesson;
import com.example.myapplication2.api.LessonAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

public class MyLessons extends AppCompatActivity {

    private TextView StudentMail;
    private TextView dateOfTheLesson;
    private TextView studyField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lessons);
        ArrayList<Lesson> lessons = new ArrayList<>();
        for(int i =0; i<10; i++) {
            lessons.add(new Lesson("Yossi", "person" +i, "java", new Date(), 100));
        }

        RecyclerView recycleView = findViewById(R.id.recyclerview_lesson);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        LessonAdapter lessonsAdapter = new LessonAdapter(lessons);
        recycleView.setAdapter(lessonsAdapter);
    }


    public void onClickAddLesson(View view) {
        AlertDialog.Builder myBuilder=new AlertDialog.Builder(MyLessons.this);
        myBuilder.setTitle("Create your lesson");
        myBuilder.create().show();
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
        if(item.getItemId() ==  R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if(item.getItemId() ==  R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if(item.getItemId() ==  R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if(item.getItemId() ==  R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        return super.onContextItemSelected(item);
    }


}