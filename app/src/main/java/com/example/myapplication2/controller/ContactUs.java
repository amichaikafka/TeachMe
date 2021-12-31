package com.example.myapplication2.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

public class ContactUs extends AppCompatActivity {
    private EditText editTextTo, editTextSubject, editTextMessage;
    private Button send;
    private boolean isTeacher;
    private final Bundle userToMove = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        savedInstanceState = getIntent().getExtras();
        isTeacher = savedInstanceState.getBoolean("user");
        userToMove.putBoolean("user", isTeacher);

        editTextTo = (EditText) findViewById(R.id.emailAddress);
        editTextSubject = (EditText) findViewById(R.id.subject);
        editTextMessage = (EditText) findViewById(R.id.message);

        send = (Button) findViewById(R.id.sendBtn);

        send.setOnClickListener(arg0 -> {
            String subject = editTextSubject.getText().toString();
            String message = editTextMessage.getText().toString();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"teachmehelpmee@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, message);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ContactUs.this, R.string.email_toset_cintact_us, Toast.LENGTH_SHORT).show();
            }
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
        if (item.getItemId() == R.id.menu_home)
            startActivity(new Intent(this, HomePage.class).putExtras(userToMove));
        if (item.getItemId() == R.id.menu_myLesson)
            startActivity(new Intent(this, MyLessons.class).putExtras(userToMove));
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