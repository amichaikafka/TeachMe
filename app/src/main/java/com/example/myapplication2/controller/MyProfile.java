package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.model.TeacherProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyProfile extends AppCompatActivity {
    private FirebaseUser mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef = null;
    private TeacherProfile currTeacher;
    private EditText subjectEt, ageEt, phoneEt, aboutMeEt, priceEt, emailEt;
    private ImageView imageView;
    private TextView nameAndFamilyT;
    private String subject, age, phone, aboutMe, price, nameAndFamily, email;
    private boolean isTeacher;
    private final Bundle userToMove = new Bundle();

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        imageView = findViewById(R.id.viewProfilePic);

        savedInstanceState = getIntent().getExtras();
        isTeacher = savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance("https://teachme-c8637-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Teachers/" + mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currTeacher = snapshot.getValue(TeacherProfile.class);
                assert currTeacher != null;
                email = currTeacher.getEmailAddress();
                subject = currTeacher.getFieldsOfTeaching();
                age = "" + currTeacher.getAge();
                phone = currTeacher.getPhoneNumber();
                aboutMe = currTeacher.getAboutMe();
                price = "" + currTeacher.getPrice();
                nameAndFamily = currTeacher.getFirstName() + " " + currTeacher.getLastName();
                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateUi() {
        subjectEt = findViewById(R.id.my_profile_subject);
        emailEt = findViewById(R.id.my_profile_email);
        ageEt = findViewById(R.id.my_profile_age);
        phoneEt = findViewById(R.id.my_profile_phone_number);
        aboutMeEt = findViewById(R.id.my_profile_about_me);
        priceEt = findViewById(R.id.my_profile_price);
        nameAndFamilyT = findViewById(R.id.my_profile_name);
        emailEt.setText(email);
        subjectEt.setText(subject);
        ageEt.setText(age);
        phoneEt.setText(phone);
        aboutMeEt.setText(aboutMe);
        priceEt.setText(price);
        nameAndFamilyT.setText(nameAndFamily);
        loadImage();
    }

    private boolean updateTeacher() {
        email = emailEt.getText().toString();
        currTeacher.setEmailAddress(email);
        nameAndFamily = nameAndFamilyT.getText().toString();
        currTeacher.setFirstName(nameAndFamily.split(" ")[0]);
        currTeacher.setLastName(nameAndFamily.split(" ")[1]);
        subject = subjectEt.getText().toString();
        currTeacher.setFieldsOfTeaching(subject);
        price = priceEt.getText().toString();
        double realPrice = 0;
        try {
            if (!price.isEmpty()) {
                realPrice = Double.parseDouble(price);
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.price_more_info, Toast.LENGTH_LONG).show();
            return false;
        }
        currTeacher.setPrice(realPrice);
        age = ageEt.getText().toString();
        try {
            if (!(age.isEmpty())) {
                Integer.parseInt(age);
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.age_toset_more_info, Toast.LENGTH_LONG).show();
            return false;
        }
        currTeacher.setAge(Integer.parseInt(age));
        phone = phoneEt.getText().toString();
        try {
            if (!(phone.isEmpty())) {
                Double.parseDouble(phone);
            }

        } catch (Exception e) {
            Toast.makeText(this, R.string.phone_toset_more_info, Toast.LENGTH_LONG).show();
            return false;
        }
        currTeacher.setPhoneNumber(phone);
        aboutMe = aboutMeEt.getText().toString();
        currTeacher.setAboutMe(aboutMe);
        return true;
    }

    public void onClickUpdate(View view) {
        if (updateTeacher()) {
            myRef.setValue(currTeacher);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicture, 0);
                }

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent();
                pickPhoto.setType("image/*");
                pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(pickPhoto, "Select Picture"), 1);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(imageBitmap);
                        imageView.setAdjustViewBounds(true);
                        imageView.setMaxHeight(500);
                        imageView.setMaxWidth(500);
                        uploadPhoto(imageBitmap);
                    }
                    break;
                case 1:
                    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                            imageView.setAdjustViewBounds(true);
                            imageView.setMaxHeight(500);
                            imageView.setMaxWidth(500);
                            uploadPhoto(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public void onPicClick(View view) {
        selectImage(MyProfile.this);
    }

    public void uploadPhoto(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] data = outputStream.toByteArray();
        String path = "profilePic/" + mAuth.getUid() + ".png";
        StorageReference storageReference = storage.getReference(path);
        UploadTask uploadTask = storageReference.putBytes(data);
    }

    public void loadImage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("profilePic/" + mAuth.getUid() + ".png").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                    imageView.setAdjustViewBounds(true);
                    imageView.setMaxHeight(500);
                    imageView.setMaxWidth(500);
                }).addOnFailureListener(exception -> {
            // Handle any errors
        });
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
        if (item.getItemId() == R.id.menu_myLesson)
            startActivity(new Intent(this, MyLessons.class).putExtras(userToMove));
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
                Intent intent = new Intent(this, MyProfile.class);
                intent.putExtras(userToMove);
                startActivity(intent);
            }
        }
        return super.onContextItemSelected(item);
    }
}