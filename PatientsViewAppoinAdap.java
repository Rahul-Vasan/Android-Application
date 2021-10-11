package com.example.fbans.projecthm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fbans.projecthm.utils.Appointment;

import java.util.ArrayList;
import java.util.List;

public class PatientsViewAppoinAdap extends RecyclerView.Adapter<PatientsViewAppoinAdap.ViewHolder> {
    List<Appointment> patientappos;
    Context context;


    public PatientsViewAppoinAdap(Context context,List<Appointment> patientappos) {
        this.context=context;
        this.patientappos = patientappos;
    }
    @NonNull

    @Override
    public PatientsViewAppoinAdap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_view_appoin_adap, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientsViewAppoinAdap.ViewHolder holder, int position) {
        Appointment appointment = patientappos.get(position);
        holder.patientName.setText(appointment.getPatname());
        holder.appointment_time.setText(appointment.getAppotime());
        holder.purpose.setText(appointment.getVisitType());
        holder.date.setText(appointment.getDate());

    }

    @Override
    public int getItemCount() {
        return patientappos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, appointment_time,purpose,date;


        public ViewHolder(View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.Patient_name_display);
            appointment_time = itemView.findViewById(R.id.appointment_time);
            purpose = itemView.findViewById(R.id.appointment_purpose);
            date = itemView.findViewById(R.id.appointment_date);

        }
    }
}
