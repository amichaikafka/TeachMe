package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.api.StudentProfile;
import com.example.myapplication2.api.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private FirebaseUser mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef = null;
    TextView hello;
    DatabaseReference subjectChecker;
    List<String> currSubjects;
    AutoCompleteTextView subjectChoose;
    String[] suggestions;
    String userType;
    Bundle userToMove=new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");
        subjectChecker = database.getReference("FieldsOfTeaching");
        subjectChecker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String curr = snapshot.getValue(String.class);
                suggestions = curr.split(",");
                subjectSearch();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        hello = (TextView) findViewById(R.id.name_filled_search);
        DatabaseReference myRef = database.getReference("Teachers/" + mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeacherProfile userProfile = snapshot.getValue(TeacherProfile.class);
                if (userProfile != null) {
                    userType="teacher";
                    userToMove.putString("user",userType);
                    hello.setText(userProfile.getFirstName());
                } else {
                    DatabaseReference myRef = database.getReference("Students/" + mAuth.getUid());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            StudentProfile userProfile = snapshot.getValue(StudentProfile.class);
                            userType="student";
                            userToMove.putString("user",userType);
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
//            subjectSearch();

    }


    public void onClickSearchForTeacher(View view) {
        String subject=subjectChoose.getText().toString().trim();
        if (Arrays.asList(suggestions).contains(subject)) {

            userToMove.putString("subject",subject);
            Intent intent=new Intent(this, HomePageNext.class);
            intent.putExtras(userToMove);
            startActivity(intent);
        }else {
            Toast.makeText(HomePage.this, "you mast choose subject from the list", Toast.LENGTH_SHORT).show();
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
        if (item.getItemId() == R.id.menu_myLesson)
            startActivity(new Intent(this, MyLessons.class));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class));
        if (item.getItemId() == R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        if (item.getItemId() == R.id.menu_myProfile){
            if(userType.equals("student")){
                Toast.makeText(this, "only teacher can edit profile", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(this, TeacherEditProfile.class);
                intent.putExtras(userToMove);
                startActivity(new Intent(this, TeacherEditProfile.class));
            }
        }
        return super.onContextItemSelected(item);
    }

    public void subjectSearch() {
        System.out.println(Arrays.toString(suggestions));
        subjectChoose=findViewById(R.id.subject_select_home);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,suggestions);
        subjectChoose.setAdapter(adapter);
//        subjectChoose = findViewById(R.id.subject_select_home);
//        System.out.println(currSubjects + "dddddddddd");
//        AutoCompleteSubjectAdapter adapter = new AutoCompleteSubjectAdapter(this, currSubjects);
//        subjectChoose.setAdapter(adapter);
    }
}