package com.example.myapplication2.controller;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;
import com.example.myapplication2.model.Lesson;
import com.example.myapplication2.model.LessonAdapter;
import com.example.myapplication2.model.StudentProfile;
import com.example.myapplication2.model.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
    private final Bundle userToMove = new Bundle();
    private final ArrayList<Lesson> lessonsArr = new ArrayList<>();
    private final LessonAdapter lessonAdapter = new LessonAdapter(lessonsArr);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lessons);
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


    public void onClickAddLesson(View view) {
        if (isTeacher) {
            createNewLessonDialog();
        }
    }

    public void createNewLessonDialog() {
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

        saveBtn.setOnClickListener(v -> {
            myQuery = database.getReference("Students/");
            myQuery.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Lesson l1 = null;
                    boolean flag = false;
                    boolean itWork = false;
                    if (checkInfo()) {
                        for (DataSnapshot student : snapshot.getChildren()) {
                            StudentProfile currStudent = student.getValue(StudentProfile.class);
                            assert currStudent != null;
                            if (currStudent.getEmailAddress().equals(lessonDialogStudentMail.getText().toString())) {
                                flag = true;
                                try {
                                    l1 = new Lesson(currTeacher.getFirstName() + " " + currTeacher.getLastName(),
                                            currStudent.getFirstName() + " " + currStudent.getLastName(),
                                            lessonDialogStudyField.getText().toString(), new Date(lessonDialogDate.getText().toString()),
                                            Integer.parseInt(lessonDialogPrice.getText().toString()));
                                    lessonsArr.add(l1);
                                    if (!currTeacher.getListOfStudents().contains(currStudent.getEmailAddress())) {
                                        DatabaseReference tempRef = database.getReference("Teachers/" + mAuth.getUid() + "/listOfStudents");
                                        if (currTeacher.getListOfStudents().isEmpty()) {
                                            currTeacher.setListOfStudents(currStudent.getEmailAddress());
                                        } else {
                                            currTeacher.setListOfStudents(currTeacher.getListOfStudents() + "," + currStudent.getEmailAddress());
                                        }
                                        tempRef.setValue(currTeacher.getListOfStudents());
                                    }
                                    itWork = true;
                                    DatabaseReference lessonRef = database.getReference("Lessons/" + student.getKey()).push();
                                    lessonRef.setValue(l1);
                                    lessonRef = database.getReference("Lessons/" + mAuth.getUid()).push();
                                    lessonRef.setValue(l1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (l1 == null) {
                            if (flag) {
                                Toast.makeText(MyLessons.this, R.string.date_toset_lesson, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    if (itWork) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            myQuery = database.getReference("Teachers/");
            myQuery.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Lesson l1 = null;
                    boolean flag = false;
                    if (checkInfo()) {
                        for (DataSnapshot student : snapshot.getChildren()) {
                            TeacherProfile currStudent = student.getValue(TeacherProfile.class);
                            assert currStudent != null;
                            if (Objects.requireNonNull(currStudent).getEmailAddress().equals(lessonDialogStudentMail.getText().toString())) {
                                flag = true;
                                try {
                                    l1 = new Lesson(currTeacher.getFirstName() + " " + currTeacher.getLastName(),
                                            currStudent.getFirstName() + " " + currStudent.getLastName(),
                                            lessonDialogStudyField.getText().toString(), new Date(lessonDialogDate.getText().toString()),
                                            Integer.parseInt(lessonDialogPrice.getText().toString()));
                                    lessonsArr.add(l1);
                                    if (!currTeacher.getListOfStudents().contains(currStudent.getEmailAddress())) {
                                        DatabaseReference tempRef = database.getReference("Teachers/" + mAuth.getUid() + "/listOfStudents");
                                        if (currTeacher.getListOfStudents().isEmpty()) {
                                            currTeacher.setListOfStudents(currStudent.getEmailAddress());
                                        } else {
                                            currTeacher.setListOfStudents(currTeacher.getListOfStudents() + "," + currStudent.getEmailAddress());
                                        }
                                        tempRef.setValue(currTeacher.getListOfStudents());
                                    }
                                    DatabaseReference lessonRef = database.getReference("Lessons/" + student.getKey()).push();
                                    lessonRef.setValue(l1);
                                    lessonRef = database.getReference("Lessons/" + mAuth.getUid()).push();
                                    lessonRef.setValue(l1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (l1 == null) {
                            if (flag) {
                                Toast.makeText(MyLessons.this, R.string.date_toset_lesson, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        cancelBtn.setOnClickListener(v -> dialog.dismiss());
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

    private boolean checkInfo() {
        if (lessonDialogStudentMail.getText().toString().isEmpty()) {
            Toast.makeText(MyLessons.this, R.string.email_toset_lesson, Toast.LENGTH_LONG).show();
            return false;
        }
        if (lessonDialogDate.getText().toString().isEmpty()) {
            Toast.makeText(MyLessons.this, R.string.date_less_toset_lesson, Toast.LENGTH_LONG).show();
            return false;
        }
        if (lessonDialogStudyField.getText().toString().isEmpty()) {
            Toast.makeText(MyLessons.this, R.string.study_field_toset_lesson, Toast.LENGTH_LONG).show();
            return false;
        }
        if (lessonDialogPrice.getText().toString().isEmpty()) {
            Toast.makeText(MyLessons.this, R.string.price_toset_lesson, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    //** menu **//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        MenuItem menuItem = menu.getItem(1);
        if (!isTeacher) {
            menuItem.setVisible(false);
        }
        return true;
    }

    //TODO: need to add case for every items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home)
            startActivity(new Intent(this, HomePage.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_contact)
            startActivity(new Intent(this, ContactUs.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_setting)
            startActivity(new Intent(this, Settings.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        if (item.getItemId() == R.id.menu_myProfile) {
            if (!isTeacher) {
                Toast.makeText(this, "only teacher can edit profile", Toast.LENGTH_LONG).show();
            } else {
                startActivity(new Intent(this, MyProfile.class).putExtras(userToMove));
            }
        }
        return super.onContextItemSelected(item);
    }
}
