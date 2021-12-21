package com.example.myapplication2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.example.myapplication2.api.Comment;
import com.example.myapplication2.api.CommentAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;

public class TeacherViewProfile extends AppCompatActivity {

    private TextView nameTextView;
    private ImageView picImageView;
    private TextView numOfReviewsTextView;
    private RatingBar ratingBar;
    private TextView filedOfStudyTextView;
    private Button phoneNumberBtn;
    private TextView aboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_profile);

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


}