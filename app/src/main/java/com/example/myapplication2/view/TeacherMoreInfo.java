package com.example.myapplication2.view;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.R;
import com.example.myapplication2.api.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherMoreInfo extends AppCompatActivity {
    private EditText subjectEt, ageEt, phoneEt, aboutMeEt,priceEt;
    private String subject, age, phone, aboutMe,price;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef =null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_more_info);
        mAuth = FirebaseAuth.getInstance();

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
            myRef = database.getReference("FieldsOfTeaching/");
            myRef.setValue(subject);
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