package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.Comment;
import com.example.myapplication2.model.CommentAdapter;
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

public class TeacherViewProfile extends AppCompatActivity {
    private FirebaseUser mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private TextView nameTextView;
    private ImageView picImageView;
    private TextView numOfReviewsTextView;
    private RatingBar ratingBar;
    private TextView filedOfStudyTextView;
    private Button phoneNumberBtn;
    private TextView aboutTextView;
    TeacherProfile otherTeacher;
    private TextView   aboutMeTv, priceTv,nameAndFamilyTv,reviewsTv;
    Button phoneBt;
    private String  reviews, phone, aboutMe, price,nameAndFamily;
    private  int rate;
    private boolean isTeacher;
    private String otherUserId;
    private RatingBar ratingBarRb;
    Bundle userToMove=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_profile);
        savedInstanceState=getIntent().getExtras();
        isTeacher =savedInstanceState.getBoolean("user");
        otherUserId=savedInstanceState.getString("otherUserId");
        userToMove.putBoolean("user", isTeacher);
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");
        myRef=database.getReference("Teachers/"+otherUserId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherTeacher=snapshot.getValue(TeacherProfile.class);
                phone=otherTeacher.getPhoneNumber();
                aboutMe=otherTeacher.getAboutMe();
                price=""+otherTeacher.getPrice()+" NIS";
                nameAndFamily=otherTeacher.getFirstName()+" "+otherTeacher.getLastName();
                reviews=""+otherTeacher.getNumOfReviews();
                rate=otherTeacher.getRating();

                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ArrayList<Comment> comments = new ArrayList<Comment>();
        for(int i=0 ; i<10 ; i++){
            comments.add(new Comment("Great teacher", "Great Math teacher.\nI recommend everyone to learn with him.", new Date(), "Israel Israeli", i));
        }

        RecyclerView recyclerView = findViewById(R.id.teacher_reviews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CommentAdapter reviewAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(reviewAdapter);
//
//        nameTextView = findViewById(R.id.viewProfileName);
//        picImageView = findViewById(R.id.viewProfilePic);
//        numOfReviewsTextView = findViewById(R.id.viewProfileNumOfReviews);
//        ratingBar = findViewById(R.id.viewProfileRatingBar);
//        filedOfStudyTextView = findViewById(R.id.viewProfileFieldOfStudy);
//        phoneNumberBtn = findViewById(R.id.viewProfilePhoneNumberBtn);
//        aboutTextView = findViewById(R.id.viewProfileAbout);

    }
    private void updateUi(){
        reviewsTv=findViewById(R.id.viewProfileNumOfReviews);
        phoneBt = findViewById(R.id.viewProfilePhoneNumberBtn);
        aboutMeTv = findViewById(R.id.viewProfileAbout);
        priceTv = findViewById(R.id.viewProfilePrice);
        nameAndFamilyTv=findViewById(R.id.viewProfileName);
        ratingBarRb=findViewById(R.id.viewProfileRatingBar);
        phoneBt.setText(phone);
        reviewsTv.setText(reviews);
        phoneBt.setText(phone);
        aboutMeTv.setText(aboutMe);
        priceTv.setText(price);
        nameAndFamilyTv.setText(nameAndFamily);

        ratingBarRb.setRating(rate);

    }
    public void onClickPhoneCall(View view){
        final int REQUEST_PHONE_CALL = 1;

        Button phoneBtn = (Button)view;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneBtn.getText().toString()));
//        callIntent.setData(Uri.parse("tel:"+phoneBtn.getText().toString()));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            startActivity(callIntent);
        }
        startActivity(callIntent);
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
                Intent intent = new Intent(this, TeacherEditProfile.class);
                intent.putExtras(userToMove);
                startActivity(intent);;
            }
        }
        return super.onContextItemSelected(item);
    }


}