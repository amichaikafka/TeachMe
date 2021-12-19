package com.example.myapplication2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

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

        nameTextView = findViewById(R.id.viewProfileName);
        picImageView = findViewById(R.id.viewProfilePic);
        numOfReviewsTextView = findViewById(R.id.viewProfileNumOfReviews);
        ratingBar = findViewById(R.id.viewProfileRatingBar);
        filedOfStudyTextView = findViewById(R.id.viewProfileFieldOfStudy);
        phoneNumberBtn = findViewById(R.id.viewProfilePhoneNumberBtn);
        aboutTextView = findViewById(R.id.viewProfileAbout);

    }


}