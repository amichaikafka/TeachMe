package com.example.myapplication2.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteSubjectAdapter extends ArrayAdapter<String> {

    private List<String> subjectListFull;
    public AutoCompleteSubjectAdapter(@NonNull Context context,  @NonNull List<String> subjectList_) {
        super(context, 0, subjectList_);
        System.out.println(subjectList_+"__________");
        this.subjectListFull =new ArrayList<>(subjectList_);
        System.out.println(subjectListFull +"zzzzzzzzzzzz");
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return subjectFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_subject_row,parent,false
            );
        }
        TextView subjectRow=convertView.findViewById(R.id.subject_row);
        String subject=getItem(position);
        if(subject!=null){
            subjectRow.setText(subject);
        }


        return convertView;
    }

    private final Filter subjectFilter=new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results= new FilterResults();
            ArrayList<String> suggestion=new ArrayList<String>();
            if(constraint==null||constraint.length()==0){
                suggestion.addAll(subjectListFull);
                System.out.println(suggestion+"xxxxxxxxxxxxxxxx");
            }else{
                String fillterSearch=constraint.toString().toLowerCase().trim();
                for(String subject: subjectListFull){
                    if(subject.toLowerCase(Locale.ROOT).contains(fillterSearch)){
                        suggestion.add(subject);
                    }
                }
            }
            results.values=suggestion;
            results.count=suggestion.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            System.out.println((List)results.values+"LLLLLLLLLLLLLLLL");

            addAll((List) results.values);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((String) resultValue);
        }
    };

}
