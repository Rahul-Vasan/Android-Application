package com.example.fbans.projecthm;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InstructiontoSta extends Fragment {
    View vi;
    EditText staffname;
    EditText patientname,roomNumber;
    EditText instruction;
    Button addinstruction;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_insttostaf,container,false);

        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staffname = vi.findViewById(R.id.staffname);
        patientname = vi.findViewById(R.id.patientname);
        roomNumber = vi.findViewById(R.id.patientroomno);
        instruction = vi.findViewById(R.id.instruction);
        addinstruction = vi.findViewById(R.id.addinstruction);
        addinstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIns();
              }


        });
        progressDialog = new ProgressDialog(getContext());
    }

    private void addIns() {
        final String staffName = staffname.getText().toString();
        final String patName = patientname.getText().toString();
        final String roomnum = roomNumber.getText().toString();
        final String ins  =  instruction.getText().toString();
        if (instruction.getText().toString().equals(""))
            Toast.makeText(getActivity(), "Enter some instruction", Toast.LENGTH_SHORT).show();
        else if (patientname.getText().toString().equals("") || patientname.getText().toString().length() < 3)
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if (!(patientname.getText().toString().matches("[a-zA-Z][a-zA-Z]*")))
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if (staffname.getText().toString().equals("") || staffname.getText().toString().length() < 3)
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if (!(staffname.getText().toString().matches("[a-zA-Z][a-zA-Z]*")))
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if (roomNumber.getText().toString().equals("")){
            Toast.makeText(getContext(),"Enter Room Number", Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Adding...");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.ADD_INS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getContext(), jsonObject.getString("re"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("Volley Error",error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("f1",staffName);
                params.put("f2",patName);
                params.put("f3",roomnum);
                params.put("f4",ins);
                return params;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(request);
        }

}
