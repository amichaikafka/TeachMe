package com.example.myapplication2.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<Review> reviews = new ArrayList<>();

    public CommentAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_review_box, parent, false);
        return new CommentViewHolder(commentView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Review currentReview = reviews.get(position);
        holder.nameTextView.setText(currentReview.getReviewerName());
        holder.subjectTextView.setText(currentReview.getSubject());
        holder.contentTextView.setText(currentReview.getReview());
        String[] date = currentReview.getDate().toString().split(" ");
        String myDate = date[0]+" "+date[1]+" "+date[2];
        holder.dateTextView.setText(myDate);
        holder.ratingBar.setRating(currentReview.getRating());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public TextView subjectTextView;
        public TextView contentTextView;
        public TextView dateTextView;
        public TextView nameTextView;
        public RatingBar ratingBar;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.review_subject);
            contentTextView = itemView.findViewById(R.id.review_content);
            dateTextView = itemView.findViewById(R.id.review_date);
            nameTextView = itemView.findViewById(R.id.reviewer_name);
            ratingBar = itemView.findViewById(R.id.review_rating);


        }
    }
}
