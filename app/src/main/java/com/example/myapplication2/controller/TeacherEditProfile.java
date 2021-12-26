package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.model.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherEditProfile extends AppCompatActivity {
    private FirebaseUser mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef = null;
    TeacherProfile currTeacher;
    private EditText subjectEt, ageEt, phoneEt, aboutMeEt, priceEt;
    TextView nameAndFamilyT;
    private String subject, age, phone, aboutMe, price,nameAndFamily;
    private boolean isTeacher;
    Bundle userToMove=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit_profile);
        savedInstanceState=getIntent().getExtras();
        isTeacher =savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");
        myRef=database.getReference("Teachers/"+mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currTeacher=snapshot.getValue(TeacherProfile.class);
                subject=currTeacher.getFieldsOfTeaching();
                age=""+currTeacher.getAge();
                phone=currTeacher.getPhoneNumber();
                aboutMe=currTeacher.getAboutMe();
                price=""+currTeacher.getPrice();
                nameAndFamily=currTeacher.getFirstName()+" "+currTeacher.getLastName();
                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateUi(){
        subjectEt = findViewById(R.id.study_profession_edit);
        ageEt = findViewById(R.id.age_edit);
        phoneEt = findViewById(R.id.phone_edit);
        aboutMeEt = (EditText)findViewById(R.id.about_me2_edit);
        priceEt = findViewById(R.id.price_edit);
        nameAndFamilyT=findViewById(R.id.name_and_family_edit);
        subjectEt.setText(subject);
        ageEt.setText(age);
        phoneEt.setText(phone);
        aboutMeEt.setText(aboutMe);
        priceEt.setText(price);
        nameAndFamilyT.setText(nameAndFamily);

    }
    private void updateTeacher(){
        subject=subjectEt.getText().toString();
        currTeacher.setFieldsOfTeaching(subject);
        price=priceEt.getText().toString();
        currTeacher.setPrice(Double.parseDouble(price));
        age=ageEt.getText().toString();
        currTeacher.setAge(Integer.parseInt(age));
        phone=phoneEt.getText().toString();
        currTeacher.setPhoneNumber(phone);
        aboutMe=aboutMeEt.getText().toString();
        currTeacher.setAboutMe(aboutMe);
    }



    public void onClickUpdate(View view) {
        Button updateBtn = (Button)view;
        updateTeacher();
        myRef.setValue(currTeacher);
    }


    //** menu **//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) startActivity(new Intent(this, HomePage.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_myLesson) startActivity(new Intent(this, MyLessons.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
//        if (item.getItemId() == R.id.menu_myProfile){startActivity(new Intent(this, TeacherEditProfile.class));}
        return super.onContextItemSelected(item);
    }
}