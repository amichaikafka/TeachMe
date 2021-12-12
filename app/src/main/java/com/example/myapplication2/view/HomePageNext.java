package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication2.R;
import com.example.myapplication2.api.TeacherProfile;
import com.example.myapplication2.api.TeachersProfilesAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class HomePageNext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_next);

        ArrayList<TeacherProfile> teachers= new ArrayList<>();
        TeacherProfile itai = new TeacherProfile("111", "Itai","Lashover",
                new Date(1,1,1),1+"@gmail.com",
                "male", "Private tutur in Java, Python, JavaScript and more...\nPrice:100 NIS per hour ", new Point(0,0), new ArrayList<String>(),
                "052-4810824",100, "empty_profile_pic");
        itai.setNumOfReviews(52);
        itai.setRating(5);
        teachers.add(itai);
        for(int i=1 ; i<20 ; i++){
            teachers.add(new TeacherProfile("111", "first"+i, "last"+i,
                    new Date(1,1,i),i+"@gmail.com",
                    "male", "nothing", new Point(i,i), new ArrayList<String>(),
                    "050-"+i+i+i+i+i+i,100+i, "empty_profile_pic"));
        }

        RecyclerView recyclerView = findViewById(R.id.teacher_search_results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TeachersProfilesAdapter teachersProfilesAdapter = new TeachersProfilesAdapter(teachers);
        recyclerView.setAdapter(teachersProfilesAdapter);
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

            case R.id.menu_home:
                intent = new Intent(HomePageNext.this, HomePage.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_myLesson:
                intent = new Intent(HomePageNext.this, MyLessons.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_setting:
                intent = new Intent(HomePageNext.this, Settings.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();

            case R.id.menu_contact:
                intent = new Intent(HomePageNext.this, ContactUs.class);
                b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
                finish();
        }

        return super.onContextItemSelected(item);
    }
}