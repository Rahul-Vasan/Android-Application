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

public class StaffViewAppoAdap extends RecyclerView.Adapter<StaffViewAppoAdap.ViewHolder> {
    List<Appointment> appodet = new ArrayList<>();
    Context context;

    public StaffViewAppoAdap(){}

    public StaffViewAppoAdap(Context context,List<Appointment> appodet){
        this.context = context;
        this.appodet = appodet;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.docviewrecadap, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Appointment appointment =appodet.get(position);
        holder.patientName.setText(appointment.getPatname());
        holder.appointment_time.setText(appointment.getAppotime());
        holder.appomobile.setText(appointment.getAge());
        holder.appotype.setText(appointment.getVisitType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id  = appointment.getId();
                String name = appointment.getPatname();
                String mobnum = appointment.getAge();
                Intent i=new Intent(context,EditAppointments.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("AID",String.valueOf(id));
                i.putExtra("name",name);
                i.putExtra("mno",mobnum);
                i.putExtra("type",appointment.getVisitType());
                i.putExtra("time",appointment.getAppotime());
                context.startActivity(i);
                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.Patient_name_display);
            appointment_time = itemView.findViewById(R.id.appointment_time);
            appomobile =itemView.findViewById(R.id.Patient_name_mobile);
            appotype = itemView.findViewById(R.id.appointment_type);
            card=itemView.findViewById(R.id.card_view);
        }
    }
}
