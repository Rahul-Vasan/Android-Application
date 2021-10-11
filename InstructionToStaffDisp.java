package com.example.fbans.projecthm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.Instructions;
import com.example.fbans.projecthm.utils.Patient;
import com.example.fbans.projecthm.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//staff name display
public class InstructionToStaffDisp extends Fragment {
    View vi;
    RecyclerView recyclerView;
    InstructionToStaffDisplayAdap adap;
    List<Instructions> instructionsList=new ArrayList<>();
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_insttostafdisplay,container,false);
        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        recyclerView = vi.findViewById(R.id.InstructionToStaffDisplay);
        recyclerView.setHasFixedSize(true);
        if (recyclerView!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        adap=new InstructionToStaffDisplayAdap();
        recyclerView.setAdapter(adap);
        instToStaff();
    }

    private void instToStaff() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        final StringRequest request = new StringRequest(Request.Method.POST, Constants.VIEW_INS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        Instructions instructions = new Instructions(object.getString("staff_name"));
                        instructionsList.add(instructions);
                    }
                    adap = new InstructionToStaffDisplayAdap(getContext(),instructionsList);
                    recyclerView.setAdapter(adap);
                    adap.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley Error:",error.getMessage());

            }
        });
        RequestHandler.getInstance(getContext()).addToRequestQueue(request);


    }
}
