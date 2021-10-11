package com.example.fbans.projecthm;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbans.projecthm.utils.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-05-2018.
 */

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.ViewHolder>  {

    List<Patient> patientList = new ArrayList<>();
    List<Patient> searchPatientList;
    Context context;

    public PatientDetailsAdapter(){}


    public PatientDetailsAdapter(Context context, List<Patient> patientList) {
        this.patientList=patientList;
        this.context=context;
    }

    @NonNull
    @Override
    public PatientDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_details_adapter, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientDetailsAdapter.ViewHolder holder, final int position) {
        final Patient patient = patientList.get(position);
        holder.patientName.setText(patient.getName());
        holder.patientAge.setText(patient.getAge());
        holder.patientGender.setText(patient.getGender());
        holder.patientMno.setText(patient.getMno());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = patientList.get(position).getId();
                String pid = String.valueOf(id);
                Toast.makeText(context, pid, Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void filterList(ArrayList<Patient> filterdNames) {
        this.patientList = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, patientAge, patientGender, patientMno;
       CardView card;


        public ViewHolder(View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.Patient_name_display);
            patientAge = itemView.findViewById(R.id.Patient_age_display);
            patientGender = itemView.findViewById(R.id.Patient_gender_display);
            patientMno = itemView.findViewById(R.id.Patient_mno_display);
            card=itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("hospmanagepref",Context.MODE_PRIVATE);
                    int temp = sharedPreferences.getInt("keyid",0);
                    Toast.makeText(context, "ID:"+String.valueOf(temp), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
