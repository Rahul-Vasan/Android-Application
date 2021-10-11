package com.example.fbans.projecthm;

import android.content.Context;
import android.content.Intent;
import android.preference.ListPreference;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fbans.projecthm.utils.Patient;
import com.example.fbans.projecthm.utils.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24-05-2018.
 */

public class StaffDetailsAdapter extends RecyclerView.Adapter<StaffDetailsAdapter.ViewHolder> {

    List<Staff> staffList=new ArrayList<>();
    Context context;

    public StaffDetailsAdapter(){}

    public StaffDetailsAdapter(Context context,List<Staff> staffList) {
        this.staffList=staffList;
        this.context = context;
    }

    @NonNull
    @Override
    public StaffDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_details_adapter, parent, false);

        return new StaffDetailsAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull StaffDetailsAdapter.ViewHolder holder, int position) {
        final Staff staff = staffList.get(position);

        holder.StaffName.setText(staff.getName());
        holder.Staffage.setText(staff.getAge());
        holder.StaffGender.setText(staff.getGender());
        holder.StaffMno.setText(staff.getMno());

    }

    public void filterList(ArrayList<Staff> filterdNames) {
        this.staffList = filterdNames;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return staffList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView StaffName, Staffage,StaffGender,StaffMno;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            StaffName = itemView.findViewById(R.id.staff_name_display);
            Staffage = itemView.findViewById(R.id.staff_age_display);
            StaffGender = itemView.findViewById(R.id.staff_gender_display);
            StaffMno = itemView.findViewById(R.id.staff_mno_display);
            card = itemView.findViewById(R.id.card_view);
        }
    }
}


