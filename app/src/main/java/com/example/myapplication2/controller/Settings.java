package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.StudentProfile;
import com.example.myapplication2.model.TeacherProfile;
import com.example.myapplication2.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class Settings extends AppCompatActivity {

    boolean flag=false;
    private FirebaseUser mAuth;
    private boolean isTeacher;
    private UserProfile  currUser;
    Bundle userToMove=new Bundle();
    FirebaseDatabase database;
    DatabaseReference myRef = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");


        savedInstanceState=getIntent().getExtras();
        isTeacher =savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);


        if(isTeacher) {
            myRef = database.getReference("Teachers/" + mAuth.getUid());
        }
        else {
            myRef = database.getReference("Students/" + mAuth.getUid());
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isTeacher) {
                    currUser = snapshot.getValue(TeacherProfile.class);
                }else {
                    currUser = snapshot.getValue(StudentProfile.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    public void onClickAboutUs(View view) {
        Intent intent = new Intent(Settings.this, AboutUs.class);
           startActivity(intent.putExtras(userToMove));
    }

    public void onClickContactUs(View view) {
        Intent intent = new Intent(Settings.this, ContactUs.class);
        startActivity(intent.putExtras(userToMove));
    }

    public void onClickChangeLanguage(View view) {


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        final String[] lang = {"English", "עברית"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Settings.this);
        myBuilder.setTitle("Choose language");
        myBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocal("en");

                    recreate();
                } else if (which == 1) {
                    setLocal("iw");
                    recreate();

                }
                dialog.dismiss();
            }
        });

        myBuilder.create().show();

    }

    public void onClickDeleteAcc(View view) {



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        final String[] lang = {"Yes", "No"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Settings.this);
        myBuilder.setTitle("Are you sure?");
        myBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //FirebaseAuth.getInstance().deleteUser(mAuth.getUid());
                    // System.out.println("Successfully deleted user.");


                }
                dialog.dismiss();
            }
        });

        myBuilder.create().show();

    }

    public void onClickChangePass(View view) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        final String[] lang = {"Yes", "No"};
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(Settings.this);
        myBuilder.setTitle("Are you sure?");
        myBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    resetPassword();


                }
                dialog.dismiss();
            }
        });

        myBuilder.create().show();

    }

    private void resetPassword (){

        FirebaseAuth.getInstance().sendPasswordResetEmail(currUser.getEmailAddress()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Settings.this, R.string.password_reset_login,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Settings.this, R.string.password_faield_reset_login,Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void setLocal(String lang) {

        Locale l = new Locale(lang);
        Locale.setDefault(l);
        Configuration config = new Configuration();
        config.locale = l;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    //** menu **//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        MenuItem menuItem=menu.getItem(1);
        if(!isTeacher){
            menuItem.setVisible(false);
        }
        return true;
    }

    //TODO: need to add case for every items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) startActivity(new Intent(this, HomePage.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_myLesson)
            startActivity(new Intent(this, MyLessons.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_contact) startActivity(new Intent(this, ContactUs.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_setting) startActivity(new Intent(this, Settings.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        if (item.getItemId() == R.id.menu_myProfile){
            if(!isTeacher){
                Toast.makeText(this, "only teacher can edit profile", Toast.LENGTH_LONG).show();
            }else {
                if (item.getItemId() == R.id.menu_setting) startActivity(new Intent(this, TeacherEditProfile.class).putExtras(userToMove));
            }
        }
        return super.onContextItemSelected(item);
    }

}