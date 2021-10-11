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

import com.example.fbans.projecthm.utils.Instructions;
import com.example.fbans.projecthm.utils.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24-05-2018.
 */

public class InstructionToStaffDisplayAdap extends RecyclerView.Adapter<InstructionToStaffDisplayAdap.ViewHolder> {
    List<Instructions> instructionList = new ArrayList<>();
    Context context;

    public InstructionToStaffDisplayAdap(){}


    public InstructionToStaffDisplayAdap(Context context,
                                 List<Instructions> instructionList) {

        this.instructionList=instructionList;
        this.context=context;
    }
    @NonNull
    @Override
    public InstructionToStaffDisplayAdap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_instruction_to_staff_display_adap, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull InstructionToStaffDisplayAdap.ViewHolder holder, final int position) {
       final Instructions ins=instructionList.get(position);
        holder.sname.setText(ins.getStaffname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id  = ins.getStaffname();
                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,InstructionsDisplay.class);
                intent.putExtra("ID",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  instructionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sname;
        public ViewHolder(View itemView) {
            super(itemView);
            sname = itemView.findViewById(R.id.sname);
        }
    } }


