package com.example.fbans.projecthm;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Appointment;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.Patient;
import com.example.fbans.projecthm.utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocViewAppo extends Fragment {

    //Button datepicker ;
    TextView date;
    private int mYear, mMonth, mDay;
    RecyclerView recyclerView;
    DocViewAppoAdap adapter = new DocViewAppoAdap();
    List<Appointment> appointments=new ArrayList<>();
    View vi;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("On Create View:","Into on Create view");
        vi = inflater.inflate(R.layout.layout_docappointview,container,false);
        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //datepicker= (Button) vi.findViewById(R.id.submit);
        recyclerView = vi.findViewById(R.id.recycler);
        if (recyclerView!=null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        recyclerView.setAdapter(adapter);

        /*datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAppo();
            }
        });*/



        date=(TextView)vi.findViewById(R.id.search_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == date) {
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    viewAppo();

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                    datePickerDialog.show();
                }
            }
        });
        progressDialog = new ProgressDialog(getContext());

    }

    public void viewAppo() {
        Log.i("Into View Appo:", "Json Parsing");
        final String apdate = date.getText().toString();

        if (date.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter date", Toast.LENGTH_LONG).show();
        }
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            final StringRequest request = new StringRequest(Request.Method.POST, Constants.VIEW_APPO_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    appointments.clear();
                    progressDialog.dismiss();
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0;i<array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            Appointment appointment = new Appointment(object.getInt("id"),object.getString("name"),object.getString("time"),
                                    object.getString("moblie"),object.getString("purpose"));
                            appointments.add(appointment);
                        }
                        if (appointments.isEmpty()){
                            Toast.makeText(getContext(), "No appointments", Toast.LENGTH_SHORT).show();
                        }
                        adapter = new DocViewAppoAdap(getContext(),appointments);
                        recyclerView.setAdapter(adapter);
                       adapter.notifyDataSetChanged();
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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params  = new HashMap<>();
                    params.put("f1",apdate);
                    return params;
                }
            };
            RequestHandler.getInstance(getContext()).addToRequestQueue(request);
    }

}
