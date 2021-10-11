package com.example.fbans.projecthm;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.Patient;
import com.example.fbans.projecthm.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatDetails extends Fragment{
    View vi;
    EditText searchText;
    RecyclerView recyclerView;
    PatientDetailsAdapter adapter = new PatientDetailsAdapter();
    List<Patient> patientArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_patientdet,container,false);
        setHasOptionsMenu(true);
        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchText = vi.findViewById(R.id.editTextSearch);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
        recyclerView = vi.findViewById(R.id.patientDetailsRecyclerView);
        recyclerView.setHasFixedSize(true);
        if (recyclerView!=null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        recyclerView.setAdapter(adapter);
        patientData();

    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Patient> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Patient row : patientArrayList) {
            //if the existing elements contains the search input
            if (row.getName().contains(text)) {
                //adding the element to filtered list
                filterdNames.add(row);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }
    public void patientData(){

        final StringRequest request = new StringRequest(Request.Method.POST, Constants.Users_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        Patient patient = new Patient(object.getInt("id"),object.getString("name"),object.getString("age"),
                                object.getString("gender"),object.getString("moblie"));
                        patientArrayList.add(patient);
                    }
                    adapter = new PatientDetailsAdapter(getContext(),patientArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error:",error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params  = new HashMap<>();
                params.put("f1","patient");
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(request);
    }
}
