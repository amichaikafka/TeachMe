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
    private String UserEmail,name,lastName,userGender;

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

    public void onClickRegistration(View view) {
        userName=(EditText)findViewById(R.id.first_name_reg);
        familyName=(EditText)findViewById(R.id.family_name_reg);
        email=(EditText) findViewById(R.id.email_reg);
        password =(EditText) findViewById(R.id.password_reg);
        passwordValid=(EditText)findViewById(R.id.password_verify_reg);
        if (userName.getText().toString().equals("")||familyName.getText().toString().equals("")||email.getText().toString().equals("")||
                password.getText().toString().equals("") ||passwordValid.getText().toString().equals("")){
            System.out.println("missing");
            return;
        }
        if( !password.getText().toString().equals( passwordValid.getText().toString())){
            System.out.println("not match password");
            return;
        }
                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println(task);
                        if (task.isSuccessful()) {
                            System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                            addUser(mAuth.getUid());
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(TeacherOrStudent.this,OpenScreen.class));
                        } else {
                            System.out.println("ssssssssssssssssssssssssssssssssss");
                            // If sign in fails, display a message to the user.
                            Toast.makeText(TeacherOrStudent.this,"failed :(",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    private void addUser(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =null;
        gender=findViewById(R.id.gender_reg);
        genderChoice=findViewById(gender.getCheckedRadioButtonId());
        studentOrTeacher = findViewById(R.id.radio_studenr_or_teacher_reg);
        studentOrTeacherChoice=findViewById(studentOrTeacher.getCheckedRadioButtonId());
        if(studentOrTeacherChoice.getText().toString().equals("Teacher")){

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