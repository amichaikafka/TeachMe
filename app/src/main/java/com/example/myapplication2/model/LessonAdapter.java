package com.example.myapplication2.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private ArrayList<Lesson> lessons;

    public LessonAdapter(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lessonView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_box,parent,false);
        return new LessonViewHolder(lessonView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson currentLesson = lessons.get(position);
        holder.teacherNameTextView.setText(currentLesson.getTeacherName());
        holder.studentNameTextView.setText(currentLesson.getStudentName());
        holder.priceTextView.setText(String.valueOf(currentLesson.getPrice()) + " NIS");
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MMM/yyyy");
        String dateOnly = dateFormat.format(currentLesson.getDate());
        holder.dateTextView.setText(dateOnly);
        holder.studyFieldTextView.setText(currentLesson.getStudyField());

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder{
        public TextView teacherNameTextView;
        public TextView studentNameTextView;
        public TextView studyFieldTextView;
        public TextView dateTextView;
        public TextView priceTextView;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherNameTextView = itemView.findViewById(R.id.lesson_box_teacher_name);
            studentNameTextView = itemView.findViewById(R.id.lesson_box_student_name);
            studyFieldTextView = itemView.findViewById(R.id.lesson_box_study_feild);
            dateTextView = itemView.findViewById(R.id.lesson_box_date_of_lesson);
            priceTextView = itemView.findViewById(R.id.lesson_box_price_nis);
        }
    }
}