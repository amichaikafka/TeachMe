package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.example.myapplication2.api.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class TeacherMoreInfo extends AppCompatActivity {
    private EditText subjectEt, ageEt, phoneEt, aboutMeEt,priceEt;
    private String subject, age, phone, aboutMe,price;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef =null;
    private FirebaseAuth mAuth;
    private DatabaseReference subjectChecker;
    String currSubject="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_more_info);
        mAuth = FirebaseAuth.getInstance();
        subjectChecker = database.getReference("FieldsOfTeaching");
        subjectChecker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String curr=snapshot.getValue(String.class);
                    currSubject=curr;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onClickUpdate(View view) {
        subjectEt = findViewById(R.id.study_profession_more_info);
        ageEt = findViewById(R.id.age_more_info);
        phoneEt = findViewById(R.id.phone_more_info);
        aboutMeEt = findViewById(R.id.about_me_more_info);
        priceEt=findViewById(R.id.price_more_info);
        if(checkInfo()) {
            myRef = database.getReference("Teachers/" + mAuth.getUid() + "/price");
            myRef.setValue(Integer.parseInt(price));
            myRef = database.getReference("Teachers/" + mAuth.getUid() + "/phoneNumber");
            myRef.setValue(phone);
            myRef = database.getReference("Teachers/" + mAuth.getUid() + "/aboutMe");
            myRef.setValue(aboutMe);
            myRef = database.getReference("Teachers/" + mAuth.getUid() + "/age");
            myRef.setValue(Integer.parseInt(age));
            myRef = database.getReference("Teachers/" + mAuth.getUid() + "/fieldsOfTeaching");
            myRef.setValue(subject);
            String[] subjects=subject.split(",");
            myRef = database.getReference("FieldsOfTeaching");
            for(String s:subjects){
                System.out.println(s);
                System.out.println(!currSubject.contains(s));
                if (!currSubject.toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))){
                    System.out.println(s);
                    s = s.toLowerCase(Locale.ROOT);
                    char c = Character.toUpperCase(s.charAt(0));
                    String ans = c + s.substring(1);
                    currSubject+=(","+ans);
                }
            }
            myRef.setValue(currSubject);
            startActivity(new Intent(TeacherMoreInfo.this, OpenScreen.class));
        }

    }
    private boolean checkInfo() {
        subject = subjectEt.getText().toString();
        age = ageEt.getText().toString();
        phone = phoneEt.getText().toString();
        aboutMe = aboutMeEt.getText().toString();
        price=priceEt.getText().toString();
        return true;

    }

    public void onClickSkip(View view) {
        Toast.makeText(TeacherMoreInfo.this, "you wont be shown..", Toast.LENGTH_LONG).show();
        startActivity(new Intent(TeacherMoreInfo.this, OpenScreen.class));

    }


    // If sign in fails


}