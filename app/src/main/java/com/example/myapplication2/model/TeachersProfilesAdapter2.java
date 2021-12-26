package com.example.myapplication2.model;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.myapplication2.R;
import com.example.myapplication2.controller.HomePageNext;
import com.example.myapplication2.controller.TeacherViewProfile;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeachersProfilesAdapter2 extends RecyclerView.Adapter<TeachersProfilesAdapter2.TeachersViewHolder2> {

    private ArrayList<TeacherProfile> teachers;

    public TeachersProfilesAdapter2(ArrayList<TeacherProfile> teachers) {
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public TeachersViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View teachersProfileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_profile_box,parent,false);
        return new TeachersViewHolder2(teachersProfileView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersViewHolder2 holder, int position) {
        TeacherProfile currentProfile = teachers.get(position);
        holder.nameTextView.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
        if(currentProfile.getAboutMe().length()>100) {
            holder.aboutMeTextView.setText(currentProfile.getAboutMe().substring(0, 100) + "...");
        }
        else{
            holder.aboutMeTextView.setText(currentProfile.getAboutMe());
        }
        holder.ratingBar.setRating(currentProfile.getRating());          //TODO: add ratings to teacher profile
        holder.numOfReviews.setText(String.valueOf(currentProfile.getNumOfReviews()));
        holder.priceTextView.setText(String.valueOf((int)(currentProfile.getPrice())) + " NIS");
        holder.phoneNumberBtn.setText(currentProfile.getPhoneNumber());
//        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("aaaaaa");
////                TextView name =(TextView) view;
////                System.out.println(name.getText());
////                String userName=name.getText().toString();
////                userToMove.putString("otherUserId",findId.get(userName));
////                startActivity(new Intent(HomePageNext.class, TeacherViewProfile.class));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public static class TeachersViewHolder2 extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView aboutMeTextView;
        public RatingBar ratingBar;
        public Button phoneNumberBtn;
        public TextView numOfReviews;
        public TextView reviewsWord;
        public ImageView profileImageView;
        public TextView priceTextView;
        public TeachersViewHolder2(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_box_teacher_name);
            aboutMeTextView = itemView.findViewById(R.id.textview_box_teacher_about);
            ratingBar = itemView.findViewById(R.id.ratingbar_box);
            phoneNumberBtn = itemView.findViewById(R.id.btn_box_phone);
            profileImageView = itemView.findViewById(R.id.viewProfilePic);
            numOfReviews = itemView.findViewById(R.id.num_of_reviews);
            reviewsWord = itemView.findViewById(R.id.review_word);
            priceTextView = itemView.findViewById(R.id.textview_box_teacher_price);
        }
    }

}
