package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.Lesson;
import com.example.myapplication2.model.LessonAdapter;
import com.example.myapplication2.model.StudentProfile;
import com.example.myapplication2.model.TeacherProfile;
import com.example.myapplication2.model.TeachersProfilesAdapter2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyLessons extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText lessonDialogStudentMail, lessonDialogDate, lessonDialogPrice, lessonDialogStudyField;
    private Button saveBtn, cancelBtn;
    private FirebaseDatabase database;
    private DatabaseReference myQuery = null;
    private TeacherProfile currTeacher;
    private boolean isTeacher;
    private FirebaseUser mAuth;
    Bundle userToMove = new Bundle();
    private ArrayList<Lesson> lessonsArr = new ArrayList<>();
    private LessonAdapter lessonAdapter = new LessonAdapter(lessonsArr);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lessons);

//        ArrayList<Lesson> lessons = new ArrayList<>();
//        for(int i =0; i<10; i++) {
//            lessons.add(new Lesson("Yossi", "person" +i, "java", new Date(), 100));
//        }
//
//        RecyclerView recycleView = findViewById(R.id.recyclerview_lesson);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recycleView.setLayoutManager(layoutManager);
//
//        LessonAdapter lessonsAdapter = new LessonAdapter(lessons);
//        recycleView.setAdapter(lessonsAdapter);
        savedInstanceState = getIntent().getExtras();
        isTeacher = savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");
        if (isTeacher) {
            myQuery = database.getReference("Teachers/" + mAuth.getUid());
            myQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currTeacher = snapshot.getValue(TeacherProfile.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        lessonsDisplay();
    }


    public void onClickAddLesson(View view) throws ParseException {
        if (isTeacher) {
            createNewLessonDialog();
        }
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
        if (item.getItemId() == R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class));
        if (item.getItemId() == R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }

        return super.onContextItemSelected(item);
    }


    public void createNewLessonDialog() throws ParseException {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_lesson);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(lessonAdapter);
        dialogBuilder = new AlertDialog.Builder(this);
        final View lessonPopupView = getLayoutInflater().inflate(R.layout.lesson_popup, null);

        lessonDialogStudentMail = (EditText) lessonPopupView.findViewById(R.id.lesson_popup_student_mail);
        lessonDialogPrice = (EditText) lessonPopupView.findViewById(R.id.lesson_popup_price);
        lessonDialogDate = (EditText) lessonPopupView.findViewById(R.id.lesson_popup_date);
        lessonDialogStudyField = (EditText) lessonPopupView.findViewById(R.id.lesson_popup_study_field);

        saveBtn = (Button) lessonPopupView.findViewById(R.id.lesson_popup_save_btn);
        cancelBtn = (Button) lessonPopupView.findViewById(R.id.lesson_popup_cancel_btn);

        dialogBuilder.setView(lessonPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQuery = database.getReference("Students/");
                myQuery.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot student : snapshot.getChildren()) {
                            StudentProfile currStudent = student.getValue(StudentProfile.class);
                            if (currStudent.getEmailAddress().equals(lessonDialogStudentMail.getText().toString())) {
                                System.out.println(currStudent.getEmailAddress());
                                Lesson l1 = new Lesson(currTeacher.getFirstName() + " " + currTeacher.getLastName(),
                                        currStudent.getFirstName() + " " + currStudent.getLastName(),
                                        lessonDialogStudyField.getText().toString(), new Date(lessonDialogDate.getText().toString()),
                                        Integer.parseInt(lessonDialogPrice.getText().toString()));
                                lessonsArr.add(l1);
                                DatabaseReference lessonRef = database.getReference("Lessons/" + student.getKey()).push();
                                lessonRef.setValue(l1);
                                lessonRef = database.getReference("Lessons/" + mAuth.getUid()).push();
                                lessonRef.setValue(l1);

                            }
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void lessonsDisplay() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_lesson);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(lessonAdapter);
        DatabaseReference lessonsRef = database.getReference("Lessons/" + mAuth.getUid());
        lessonsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lessonsArr.clear();
                for (DataSnapshot lessons : snapshot.getChildren()) {
                    System.out.println(lessons.getValue());
                    Lesson currLesson = (Lesson) lessons.getValue(Lesson.class);
                    lessonsArr.add(currLesson);
                }
                lessonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}