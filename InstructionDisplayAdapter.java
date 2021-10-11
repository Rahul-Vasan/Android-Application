package com.example.fbans.projecthm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fbans.projecthm.utils.Instructions;

import java.util.ArrayList;
import java.util.List;

public class InstructionDisplayAdapter extends RecyclerView.Adapter<InstructionDisplayAdapter.ViewHolder>{

    List<Instructions> instructions = new ArrayList<>();
    Context context;

    public InstructionDisplayAdapter(){}
    public InstructionDisplayAdapter(Context context,List<Instructions> instructions){
        this.context = context;
        this.instructions = instructions;
    }
    @NonNull
    @Override
    public InstructionDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.instructions_display,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionDisplayAdapter.ViewHolder viewHolder, int pos) {
        Instructions ins = instructions.get(pos);
        viewHolder.pname.setText(ins.getPatname());
        viewHolder.proom.setText(ins.getRoomnum());
        viewHolder.ins.setText(ins.getInstruction());

    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pname,proom,ins;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.pname);
            proom = itemView.findViewById(R.id.proom);
            ins = itemView.findViewById(R.id.pins);
        }
    }
}
