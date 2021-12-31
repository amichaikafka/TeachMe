package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    private String subject;
    private boolean isTeacher;
    private final Bundle userToMove = new Bundle();

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_next);
        savedInstanceState = getIntent().getExtras();
        subject = savedInstanceState.getString("subject");
        isTeacher = savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);
        searchName = findViewById(R.id.searchView_search);
        System.out.println(searchName.toString());
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");

        sortGroup = findViewById(R.id.radio_sort);
        sortGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_sort_name:
                    sortParam = "firstName";
                    break;
                case R.id.radio_sort_price:
                    sortParam = "price";
                    break;
                case R.id.radio_sort_rating:
                    sortParam = "rating";
                    break;
            }
            teachersDisplay();
        });
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchVal = query;
                sortBy();
                teachersDisplay();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchVal = newText;
                sortBy();
                teachersDisplay();
                return false;
            }
        });
        teachersDisplay();
    }

    public void sortBy() {
        sortBy = findViewById(sortGroup.getCheckedRadioButtonId());
        if (sortGroup.getCheckedRadioButtonId() != -1) {
            if (sortBy.getText().toString().trim().equals("Price") || sortBy.getText().toString().trim().equals("Rate")) {
                sortParam = sortBy.getText().toString().trim().toLowerCase(Locale.ROOT);
            } else {
                sortParam = "firstName";
            }
        }
    }

    public void teachersDisplay() {
        ArrayList<TeacherProfile> teachers = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.teacher_search_results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TeachersProfilesAdapter teachersProfilesAdapter = new TeachersProfilesAdapter(teachers);
        recyclerView.setAdapter(teachersProfilesAdapter);
        myQuery = database.getReference("Teachers").orderByChild(sortParam);
        myQuery.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teachers.clear();
                for (DataSnapshot teacher : snapshot.getChildren()) {
                    TeacherProfile currTeacher = teacher.getValue(TeacherProfile.class);

                    searchVal = searchVal.toLowerCase(Locale.ROOT);
                    subject = subject.toLowerCase(Locale.ROOT);
                    assert currTeacher != null;
                    String nameAndFamily = currTeacher.getFirstName().toLowerCase(Locale.ROOT) + " " + currTeacher.getLastName().toLowerCase(Locale.ROOT);
                    if (nameAndFamily.contains(searchVal) && currTeacher.getFieldsOfTeaching().toLowerCase(Locale.ROOT).contains(subject)) {
                        currTeacher.setUserID(teacher.getKey());
                        if (sortParam.equals("rating")) {
                            teachers.add(0, currTeacher);

                        } else {
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


    public void onClickPhoneCall(View view) {
        final int REQUEST_PHONE_CALL = 1;

        Button phoneBtn = (Button) view;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneBtn.getText().toString()));
        if (ContextCompat.checkSelfPermission(HomePageNext.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomePageNext.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        } else {
            startActivity(callIntent);
        }
        startActivity(callIntent);
    }

    public class TeachersProfilesAdapter extends RecyclerView.Adapter<TeachersProfilesAdapter.TeachersViewHolder> {

        private final ArrayList<TeacherProfile> teachers;

        public TeachersProfilesAdapter(ArrayList<TeacherProfile> teachers) {
            this.teachers = teachers;
        }

        @NonNull
        @Override
        public TeachersProfilesAdapter.TeachersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View teachersProfileView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.teacher_profile_box, parent, false);
            return new TeachersProfilesAdapter.TeachersViewHolder(teachersProfileView);
        }

        @Override
        public void onBindViewHolder(@NonNull TeachersProfilesAdapter.TeachersViewHolder holder, int position) {
            TeacherProfile currentProfile = teachers.get(position);
            holder.nameTextView.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
            if (currentProfile.getAboutMe().length() > 100) {
                holder.aboutMeTextView.setText(currentProfile.getAboutMe().substring(0, 100) + "...");
            } else {
                holder.aboutMeTextView.setText(currentProfile.getAboutMe());
            }
            holder.ratingBar.setRating(currentProfile.getRating());          //TODO: add ratings to teacher profile
            holder.numOfReviews.setText(String.valueOf(currentProfile.getNumOfReviews()));
            holder.priceTextView.setText((int) (currentProfile.getPrice()) + " NIS");
            holder.phoneNumberBtn.setText(currentProfile.getPhoneNumber());
            holder.nameTextView.setOnClickListener(v -> {

                userToMove.putString("otherUserId", currentProfile.getUserID());
                startActivity(new Intent(HomePageNext.this, TeacherViewProfile.class).putExtras(userToMove));
            });
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            try {
                storageReference.child("profilePic/" + currentProfile.getUserID() + ".png").getBytes(Long.MAX_VALUE)
                        .addOnSuccessListener(bytes -> {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            holder.profileImageView.setImageBitmap(bitmap);
                            holder.profileImageView.setAdjustViewBounds(true);
                            holder.profileImageView.setMaxHeight(90);
                            holder.profileImageView.setMaxWidth(90);
                            holder.profileImageView.setVisibility(View.VISIBLE);
                            holder.progressBar.setVisibility(View.GONE);
                        }).addOnFailureListener(exception -> {
                    holder.profileImageView.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.GONE);
                });
            } catch (Exception ignored) {
            }
        }

        @Override
        public int getItemCount() {
            return teachers.size();
        }

        public class TeachersViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;
            public TextView aboutMeTextView;
            public RatingBar ratingBar;
            public Button phoneNumberBtn;
            public TextView numOfReviews;
            public TextView reviewsWord;
            public ImageView profileImageView;
            public TextView priceTextView;
            public ProgressBar progressBar;

            public TeachersViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.textview_box_teacher_name);
                aboutMeTextView = itemView.findViewById(R.id.textview_box_teacher_about);
                ratingBar = itemView.findViewById(R.id.ratingbar_box);
                phoneNumberBtn = itemView.findViewById(R.id.btn_box_phone);
                profileImageView = itemView.findViewById(R.id.viewProfilePic);
                numOfReviews = itemView.findViewById(R.id.num_of_reviews);
                reviewsWord = itemView.findViewById(R.id.review_word);
                priceTextView = itemView.findViewById(R.id.textview_box_teacher_price);
                progressBar = itemView.findViewById(R.id.progressBar_teacherbox);
            }
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
