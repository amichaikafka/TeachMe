package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.Review;
import com.example.myapplication2.model.CommentAdapter;
import com.example.myapplication2.model.StudentProfile;
import com.example.myapplication2.model.TeacherProfile;
import com.example.myapplication2.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

public class TeacherViewProfile extends AppCompatActivity {
    private FirebaseUser mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private final Bundle userToMove = new Bundle();

    private UserProfile currUser;
    private TeacherProfile otherTeacher;

    private String reviews, phone, aboutMe, price, nameAndFamily;
    private String otherUserId;
    private float rate;
    private boolean isTeacher;

    private TextView aboutMeTv, priceTv, nameAndFamilyTv, reviewsTv;
    private EditText commentDialogSubject, commentDialogReview;
    private ImageView picImageView;
    private Button saveBtn, cancelBtn;
    private RatingBar commentDialogRate;
    private ProgressBar progressBar;
    private Button phoneBt;
    private RatingBar ratingBarRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_profile);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");
        savedInstanceState = getIntent().getExtras();
        isTeacher = savedInstanceState.getBoolean("user");
        otherUserId = savedInstanceState.getString("otherUserId");
        userToMove.putBoolean("user", isTeacher);
        if (isTeacher) {
            myRef = database.getReference("Teachers/" + mAuth.getUid());
        } else {
            myRef = database.getReference("Students/" + mAuth.getUid());
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isTeacher) {
                    currUser = snapshot.getValue(TeacherProfile.class);
                } else {
                    currUser = snapshot.getValue(StudentProfile.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef = database.getReference("Teachers/" + otherUserId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherTeacher = snapshot.getValue(TeacherProfile.class);
                assert otherTeacher != null;
                phone = otherTeacher.getPhoneNumber();
                aboutMe = otherTeacher.getAboutMe();
                price = "" + otherTeacher.getPrice() + " NIS";
                nameAndFamily = otherTeacher.getFirstName() + " " + otherTeacher.getLastName();
                reviews = "" + otherTeacher.getNumOfReviews();
                rate = otherTeacher.getRating();

                updateUi();
                commentDisplay();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void commentDisplay() {
        ArrayList<Review> reviews = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.teacher_reviews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter reviewAdapter = new CommentAdapter(reviews);
        recyclerView.setAdapter(reviewAdapter);
        DatabaseReference lessonsRef = database.getReference("Reviews/" + otherUserId);
        lessonsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviews.clear();
                for (DataSnapshot comment : snapshot.getChildren()) {
                    System.out.println(comment.getValue());
                    Review currReview = comment.getValue(Review.class);
                    reviews.add(0, currReview);
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateUi() {
        reviewsTv = findViewById(R.id.viewProfileNumOfReviews);
        phoneBt = findViewById(R.id.viewProfilePhoneNumberBtn);
        aboutMeTv = findViewById(R.id.viewProfileAbout);
        priceTv = findViewById(R.id.viewProfilePrice);
        nameAndFamilyTv = findViewById(R.id.viewProfileName);
        ratingBarRb = findViewById(R.id.viewProfileRatingBar);
        phoneBt.setText(phone);
        reviewsTv.setText(reviews);
        phoneBt.setText(phone);
        aboutMeTv.setText(aboutMe);
        priceTv.setText(price);
        nameAndFamilyTv.setText(nameAndFamily);
        ratingBarRb.setRating(rate);
        picImageView = findViewById(R.id.viewProfilePic);
        progressBar = findViewById(R.id.progressBar_teacherView);
        loadImage();
    }

    public void onClickPhoneCall(View view) {
        final int REQUEST_PHONE_CALL = 1;

        Button phoneBtn = (Button) view;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBtn.getText().toString()));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        } else {
            startActivity(callIntent);
        }
        startActivity(callIntent);
    }

    public void loadImage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("profilePic/" + otherUserId + ".png").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    picImageView.setImageBitmap(bitmap);
                    picImageView.setAdjustViewBounds(true);
                    picImageView.setMaxHeight(115);
                    picImageView.setMaxWidth(115);
                    picImageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }).addOnFailureListener(exception -> {
            picImageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        });
    }

    public void onClickAddComment(View view) {
        if (otherTeacher.getListOfStudents().contains(currUser.getEmailAddress())) {
            dialogBuilder = new AlertDialog.Builder(this);
            final View lessonPopupView = getLayoutInflater().inflate(R.layout.review_popup, null);

            commentDialogRate = lessonPopupView.findViewById(R.id.ratingBar_popup_comment);
            commentDialogSubject = (EditText) lessonPopupView.findViewById(R.id.subject_popup_comment);
            commentDialogReview = (EditText) lessonPopupView.findViewById(R.id.review_popup_comment);

            saveBtn = (Button) lessonPopupView.findViewById(R.id.save_popup_comment);
            cancelBtn = (Button) lessonPopupView.findViewById(R.id.close_popup_comment);

            dialogBuilder.setView(lessonPopupView);
            dialog = dialogBuilder.create();
            dialog.show();
            database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");


            saveBtn.setOnClickListener(v -> {
                otherTeacher.setNumOfReviews(otherTeacher.getNumOfReviews() + 1);
                otherTeacher.setSumOfReviews(otherTeacher.getSumOfReviews() + commentDialogRate.getRating());
                otherTeacher.setRating(otherTeacher.getSumOfReviews() / otherTeacher.getNumOfReviews());
                myRef = database.getReference("Reviews/" + otherUserId).push();
                Review review = new Review(commentDialogSubject.getText().toString(),
                        commentDialogReview.getText().toString(),
                        new Date(), currUser.getFirstName() + " " + currUser.getLastName(), commentDialogRate.getRating());
                myRef.setValue(review);
                myRef = database.getReference("Teachers/" + otherUserId + "/numOfReviews");
                myRef.setValue(otherTeacher.getNumOfReviews());
                myRef = database.getReference("Teachers/" + otherUserId + "/sumOfReviews");
                myRef.setValue(otherTeacher.getSumOfReviews());
                myRef = database.getReference("Teachers/" + otherUserId + "/rating");
                myRef.setValue((otherTeacher.getRating()));
                dialog.dismiss();
            });

            cancelBtn.setOnClickListener(v -> dialog.dismiss());
        } else {
            Toast.makeText(this, R.string.student_toset_profile_view, Toast.LENGTH_LONG).show();

        }
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
        if (item.getItemId() == R.id.menu_myLesson)
            startActivity(new Intent(this, MyLessons.class).putExtras(userToMove));
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
                Intent intent = new Intent(this, MyProfile.class);
                intent.putExtras(userToMove);
                startActivity(intent);
            }
        }
        return super.onContextItemSelected(item);
    }
}