package com.example.fbans.projecthm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbans.projecthm.utils.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 22-05-2018.
 */

public class DocViewAppoAdap extends RecyclerView.Adapter<DocViewAppoAdap.ViewHolder> {
    List<Appointment> appodet = new ArrayList<>();
    Context context;

    public DocViewAppoAdap(){ }

    public DocViewAppoAdap(Context context,
                           List<Appointment> appodet) {

        this.appodet=appodet;
        this.context=context;
    }
    @NonNull

    @Override
    public DocViewAppoAdap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.docviewrecadap, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull DocViewAppoAdap.ViewHolder holder, int position) {

        final Appointment appointment =appodet.get(position);
        holder.patientName.setText(appointment.getPatname());
        holder.appointment_time.setText(appointment.getAppotime());
        holder.appomobile.setText(appointment.getAge());
        holder.appotype.setText(appointment.getVisitType());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = appointment.getId();
                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
                /*Intent i=new Intent(context,AppointmentDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return appodet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, appointment_time,appomobile,appotype;
       CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.Patient_name_display);
            appointment_time = itemView.findViewById(R.id.appointment_time);
            appomobile =itemView.findViewById(R.id.Patient_name_mobile);
            appotype = itemView.findViewById(R.id.appointment_type);
            card=itemView.findViewById(R.id.card_view);
        }
    }
}
