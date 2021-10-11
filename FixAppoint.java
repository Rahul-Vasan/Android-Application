package com.example.fbans.projecthm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.RequestHandler;
import com.example.fbans.projecthm.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FixAppoint extends Fragment {
    View vi;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView appointment_date, custom_time,name,mobile_no;
    Spinner spinner;
    String spinnerValue;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_fixappoint,container,false);

        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (TextView) vi.findViewById(R.id.patient_name);
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("hospmanagepref", Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString("keyusername",null);
        name.setText(temp);
        mobile_no = (TextView) vi.findViewById(R.id.patient_mobileno);
        SharedPreferences sharedPreferences1 =getActivity().getSharedPreferences("hospmanagepref", Context.MODE_PRIVATE);
        String temp1 = sharedPreferences.getString("keynum",null);
        mobile_no.setText(temp1);
        appointment_date = (TextView) vi.findViewById(R.id.patient_date);
        custom_time = (TextView) vi.findViewById(R.id.Patient_time);
        spinner = (Spinner) vi.findViewById(R.id.spinner);
        progressDialog = new ProgressDialog(getContext());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                appointment_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });
        custom_time.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (view == custom_time) {

                                                   // Get Current Time
                                                   final Calendar c = Calendar.getInstance();
                                                   mHour = c.get(Calendar.HOUR_OF_DAY);
                                                   mMinute = c.get(Calendar.MINUTE);

                                                   // Launch Time Picker Dialog
                                                   TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                                           new TimePickerDialog.OnTimeSetListener() {

                                                               @Override
                                                               public void onTimeSet(TimePicker view, int hourOfDay,
                                                                                     int minute) {
                                                                   Calendar time = Calendar.getInstance();
                                                                   time.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                                                   time.set(Calendar.MINUTE,minute);
                                                                   if (time.getTimeInMillis()>c.getTimeInMillis())
                                                                   custom_time.setText(hourOfDay + ":" + minute);
                                                                   else
                                                                       custom_time.setText("Invalid");
                                                               }
                                                           }, mHour, mMinute, false);
                                                   timePickerDialog.show();
                                               }
                                           }
                                       }
        );


        Button fix = (Button) vi.findViewById(R.id.button1);
        Button viewap = (Button) vi.findViewById(R.id.button2);
        fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixAppo();
            }


        });
        viewap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.main_frame ,new PatientVA())
                        .commit();
            }
        });
    }

    public void fixAppo(){

        final String patname = name.getText().toString();
        final String mno =  mobile_no.getText().toString();
        final String adate = appointment_date.getText().toString();
        final String atime = custom_time.getText().toString();
        if (name.getText().toString().equals("") || name.getText().toString().length() < 3){
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();}

        else if (!(name.getText().toString().matches("[a-zA-Z][a-zA-Z]*"))){
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();}

        else if (mobile_no.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Enter a mobile no", Toast.LENGTH_SHORT).show();}
        else if(spinnerValue.equals("")){
            Toast.makeText(getActivity(), "Select type", Toast.LENGTH_SHORT).show();
        }

        else if (mobile_no.getText().toString().length() != 10 || mobile_no.getText().toString().length()>10){
            Toast.makeText(getActivity(), "Invalid mobile no", Toast.LENGTH_SHORT).show();}

        else if (appointment_date.getText().toString().equals("") || appointment_date.getText().toString().equals("Date")){
            Toast.makeText(getActivity(), "Enter a appointment date", Toast.LENGTH_SHORT).show();}

        else if (custom_time.getText().toString().equals("") || custom_time.getText().toString().equals("Invalid") || custom_time.getText().toString().equals("Time"))
        {Toast.makeText(getActivity(), "Enter a appointment time", Toast.LENGTH_SHORT).show();}

        else {
        progressDialog.setMessage("Fixing Appointment...");
        progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.FixAppo_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        progressDialog.hide();
                        JSONObject object = new JSONObject(response);
                        if (object.getString("re").equalsIgnoreCase("Success !")){
                            Toast.makeText(getContext(), "Fixed", Toast.LENGTH_SHORT).show();
                            appointment_date.setText("Date");
                            custom_time.setText("Time");
                        }else
                        Toast.makeText(getContext(), object.getString("re"), Toast.LENGTH_SHORT).show();
                    }
                    catch (JSONException e) {
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
                    params.put("d1",patname);
                    params.put("d2",mno);
                    params.put("d3",spinnerValue);
                    params.put("d4",adate);
                    params.put("d5",atime);
                    return params;

                }
            };

            RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);


        }

    }
}
