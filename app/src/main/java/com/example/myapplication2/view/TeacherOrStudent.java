package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.api.StudentProfile;
import com.example.myapplication2.api.TeacherProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;

public class TeacherOrStudent extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText userName;
    private EditText familyName;
    private EditText email;
    private EditText password ;
    private EditText passwordValid;
    private RadioGroup studentOrTeacher;
    private RadioButton studentOrTeacherChoice;
    private RadioGroup gender;
    private RadioButton genderChoice;
    private String userEmail,name,lastName,userGender,userPassword,userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_or_student);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickBack(View view) {
        Intent intent=new Intent(TeacherOrStudent.this, OpenScreen.class);
        Bundle b=new Bundle();
        b.putInt("R",1);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    private void update(){
        userName=(EditText)findViewById(R.id.first_name_reg);
        familyName=(EditText)findViewById(R.id.family_name_reg);
        email=(EditText) findViewById(R.id.email_reg);
        password =(EditText) findViewById(R.id.password_reg);
        passwordValid=(EditText)findViewById(R.id.password_verify_reg);
        gender=findViewById(R.id.gender_reg);
        genderChoice=findViewById(gender.getCheckedRadioButtonId());
//        studentOrTeacher = findViewById(R.id.radio_studenr_or_teacher_reg);
        studentOrTeacherChoice=findViewById(studentOrTeacher.getCheckedRadioButtonId());
    }

    private boolean checkInfo(){
        name=userName.getText().toString();
        lastName=familyName.getText().toString();
        userEmail= email.getText().toString().trim();
        userPassword=password.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(TeacherOrStudent.this,"You must to fill the Name field",Toast.LENGTH_LONG).show();
            return false;
        }
        if(lastName.isEmpty()){
            Toast.makeText(TeacherOrStudent.this,"You must to fill the Family name field",Toast.LENGTH_LONG).show();
            return false;
        }
        if(userEmail.isEmpty()){
            Toast.makeText(TeacherOrStudent.this,"You must to fill the Email field",Toast.LENGTH_LONG).show();
            return false;
        }
        if(userPassword.isEmpty()){
            Toast.makeText(TeacherOrStudent.this,"You must to fill the Password field",Toast.LENGTH_LONG).show();
            return false;
        }
        if (userPassword.length()<6) {
            Toast.makeText(TeacherOrStudent.this, "Passwords must to be at list 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!userPassword.equals(passwordValid.getText().toString().toString())){
            Toast.makeText(TeacherOrStudent.this,"Password verification must match the selected password",Toast.LENGTH_LONG).show();
            return false;
        }
        if (studentOrTeacher.getCheckedRadioButtonId()==-1){
            Toast.makeText(TeacherOrStudent.this,"you must to chose Teacher/Student",Toast.LENGTH_LONG).show();
            return false;
        }
        if (gender.getCheckedRadioButtonId()==-1){
            Toast.makeText(TeacherOrStudent.this,"you must to chose gender type",Toast.LENGTH_LONG).show();
            return false;
        }

        userRole=studentOrTeacherChoice.getText().toString().trim();
        userGender=genderChoice.getText().toString().trim();
        return true;
    }

    public void onClickRegistration(View view) {
        update();
        if(checkInfo()) {
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                addUser(mAuth.getUid());
                                // Sign in success, update UI with the signed-in user's information
                                startActivity(new Intent(TeacherOrStudent.this, OpenScreen.class));
                            } else {

                                // If sign in fails
                                Toast.makeText(TeacherOrStudent.this, "failed :(", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

    }

    private void addUser(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =null;
        if(userRole.equals("Teacher")){
            TeacherProfile teacher=new TeacherProfile(userName.getText().toString(),familyName.getText().toString()
                    ,email.getText().toString(),genderChoice.getText().toString());
            myRef = database.getReference("Teachers/"+id);
            myRef.setValue(teacher);
        }else{
            StudentProfile student=new StudentProfile(userName.getText().toString(),familyName.getText().toString()
                    ,email.getText().toString(),genderChoice.getText().toString());
            myRef = database.getReference("Students/"+id);
            myRef.setValue(student);
        }
    }

}