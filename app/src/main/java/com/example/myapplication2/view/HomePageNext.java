package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.example.myapplication2.R;
import com.example.myapplication2.api.TeacherProfile;
import com.example.myapplication2.api.TeachersProfilesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class HomePageNext extends AppCompatActivity {
    private SearchView searchName;
    private FirebaseDatabase database;
    private Query myQuery = null;
    private String searchVal = "";
    private RadioGroup sortGroup;
    private RadioButton sortBy;
    private String sortParam = "firstName";

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_next);
        searchName = findViewById(R.id.searchView_search);
        System.out.println(searchName.toString());
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");

        sortGroup = findViewById(R.id.radio_sort);
        sortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                sortBy();
                switch (checkedId){
                    case R.id.radio_sort_name:
                        sortParam="firstName";
                        break;
                    case R.id.radio_sort_price:
                        sortParam="price";
                        break;
                    case R.id.radio_sort_rating:
                        sortParam="rating";
                        break;
                }
                techersDisplay();
            }
        });
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                System.out.println(query);
                searchVal = query;
                sortBy();
                techersDisplay();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                System.out.println(newText+" _________");
                searchVal = newText;
                sortBy();
                techersDisplay();
                return false;
            }
        });

        techersDisplay();

//        ArrayList<TeacherProfile> teachers= new ArrayList<>();
//        TeacherProfile itai = new TeacherProfile("111", "Itai","Lashover",
//                new Date(1,1,1),1+"@gmail.com",
//                "male", "Private tutur in Java, Python, JavaScript and more...\nPrice:100 NIS per hour ", new Point(0,0), new ArrayList<String>(),
//                "052-4810824",100, "empty_profile_pic");
//        itai.setNumOfReviews(52);
//        itai.setRating(5);
//        teachers.add(itai);
//        for(int i=1 ; i<20 ; i++){
//            teachers.add(new TeacherProfile("111", "first"+i, "last"+i,
//                    new Date(1,1,i),i+"@gmail.com",
//                    "male", "nothing", new Point(i,i), new ArrayList<String>(),
//                    "050-"+i+i+i+i+i+i,100+i, "empty_profile_pic"));
//        }


//        myQuery.addChildEventListener(new ChildEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                TeacherProfile teacherProfile=snapshot.getValue(TeacherProfile.class);
//                teachers.add(teacherProfile);
//                teachersProfilesAdapter.notifyDataSetChanged();
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                TeacherProfile teacherProfile=snapshot.getValue(TeacherProfile.class);
//                teachers.add(teacherProfile);
//                teachersProfilesAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void sortBy() {
        sortBy = findViewById(sortGroup.getCheckedRadioButtonId());
        if (sortGroup.getCheckedRadioButtonId()!=-1) {
            if (sortBy.getText().toString().trim().equals("Price") || sortBy.getText().toString().trim().equals("Rate")) {
                sortParam = sortBy.getText().toString().trim().toLowerCase(Locale.ROOT);
            }else{
                sortParam="firstName";
            }
        }
    }

    public void techersDisplay() {
        ArrayList<TeacherProfile> teachers = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.teacher_search_results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TeachersProfilesAdapter teachersProfilesAdapter = new TeachersProfilesAdapter(teachers);
        recyclerView.setAdapter(teachersProfilesAdapter);
        System.out.println(sortParam+"________");
        myQuery = database.getReference("Teachers").orderByChild(sortParam);
        myQuery.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teachers.clear();
                for (DataSnapshot teacher : snapshot.getChildren()) {
                    TeacherProfile currTeacher = teacher.getValue(TeacherProfile.class);
//                    System.out.println(currTeacher.getFirstName()+" "+searchVal);
                    searchVal = searchVal.toLowerCase(Locale.ROOT);
                    String nameAndFamily = currTeacher.getFirstName().toLowerCase(Locale.ROOT) + " " + currTeacher.getLastName().toLowerCase(Locale.ROOT);
                    if (nameAndFamily.contains(searchVal)) {
                        if (sortParam.equals("rating")) {
                            teachers.add(0,currTeacher);
                        }else {
                            teachers.add(currTeacher);
                        }
                    }
                }
                teachersProfilesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        if (item.getItemId() == R.id.menu_myLesson)
            startActivity(new Intent(this, MyLessons.class));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class));
        if (item.getItemId() == R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, OpenScreen.class));
        }
        return super.onContextItemSelected(item);
    }
}
