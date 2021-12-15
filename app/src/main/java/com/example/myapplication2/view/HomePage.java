package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.api.StudentProfile;
import com.example.myapplication2.api.TeacherProfile;
import com.example.myapplication2.api.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomePage extends AppCompatActivity {
    private FirebaseUser mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef=null;
    TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        hello=(TextView)findViewById(R.id.name_filled_search);
        database= FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");

        DatabaseReference myRef = database.getReference("Teachers/"+mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeacherProfile userProfile=snapshot.getValue(TeacherProfile.class);
                if (userProfile!=null){
                    hello.setText(userProfile.getFirstName());
                }else{
                    DatabaseReference myRef = database.getReference("Students/"+mAuth.getUid());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            StudentProfile userProfile=snapshot.getValue(StudentProfile.class);
                            hello.setText(userProfile.getFirstName());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(HomePage.this, "error reading from firebase", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "error reading from firebase", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void onClickSearchForTeacher(View view) {
        startActivity(new Intent(this, HomePageNext.class));
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
        if(item.getItemId() ==  R.id.menu_myLesson) startActivity(new Intent(this, MyLessons.class));
        if(item.getItemId() ==  R.id.menu_contact) startActivity(new Intent(this, ContactUs.class));
        if(item.getItemId() ==  R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if(item.getItemId() ==  R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, OpenScreen.class));
        }
        return super.onContextItemSelected(item);
    }

}