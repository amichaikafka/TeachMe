package com.example.myapplication2.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.myapplication2.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeachersProfilesAdapter extends RecyclerView.Adapter<TeachersProfilesAdapter.TeachersViewHolder> {

    private ArrayList<TeacherProfile> teachers;

    public TeachersProfilesAdapter(ArrayList<TeacherProfile> teachers) {
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public TeachersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View teachersProfileView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_profile_box,parent,false);
        return new TeachersViewHolder(teachersProfileView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersViewHolder holder, int position) {
        TeacherProfile currentProfile = teachers.get(position);
        holder.nameTextView.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
        holder.aboutMeTextView.setText(currentProfile.getAboutMe());
        holder.ratingBar.setRating(3);          //TODO: add ratings to teacher profile
        holder.phoneNumberBtn.setText(currentProfile.getPhoneNumber());
        holder.profileImageView.setImageResource(holder.nameTextView.getResources()
                .getIdentifier(currentProfile.getProfilePicture(), "drawable",
                        holder.nameTextView.getContext().getPackageName()));
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public static class TeachersViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public TextView aboutMeTextView;
        public RatingBar ratingBar;
        public Button phoneNumberBtn;
        public ImageView profileImageView;
        public TeachersViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textview_box_teacher_name);
            aboutMeTextView = itemView.findViewById(R.id.textview_box_teacher_about);
            ratingBar = itemView.findViewById(R.id.ratingbar_box);
            phoneNumberBtn = itemView.findViewById(R.id.btn_box_phone);
            profileImageView = itemView.findViewById(R.id.imageview_box_teacher_pic);

        }
    }

}
