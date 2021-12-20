package com.example.myapplication2.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.google.firebase.auth.FirebaseAuth;

public class ContactUs extends AppCompatActivity {
    EditText editTextTo,editTextSubject,editTextMessage;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        editTextTo=(EditText)findViewById(R.id.emailAddress);
        editTextSubject=(EditText)findViewById(R.id.subject);
        editTextMessage=(EditText)findViewById(R.id.message);

        send=(Button)findViewById(R.id.sendBtn);

        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                String to=editTextTo.getText().toString();
                String subject=editTextSubject.getText().toString();
                String message=editTextMessage.getText().toString();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"teachmehelpmee@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, subject);
                i.putExtra(Intent.EXTRA_TEXT   , message);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactUs.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
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
        if(item.getItemId() ==  R.id.menu_myLesson) startActivity(new Intent(this, MyLessons.class));
        if(item.getItemId() ==  R.id.menu_home) startActivity(new Intent(this, HomePage.class));
        if(item.getItemId() ==  R.id.menu_setting) startActivity(new Intent(this, Settings.class));
        if(item.getItemId() ==  R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
        }
        return super.onContextItemSelected(item);
    }
}