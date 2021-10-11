package com.example.fbans.projecthm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditAppointments extends AppCompatActivity {
    View vi;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView appointment_date, custom_time;
    TextView name, mobile_no,type;
    Spinner spinner;
    String spinnerValue;
    ProgressDialog progressDialog;
    String uname,umobile,utype,utime,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointments);
        final Bundle intent = getIntent().getExtras();
        uname = intent.getString("name");
        umobile = intent.getString("mno");
        utype = intent.getString("type");
        utime = intent.getString("time");
        uid = intent.getString("AID");

        name = (TextView) findViewById(R.id.patient_name);
        name.setText(uname);
        name.setEnabled(false);
        mobile_no = (TextView) findViewById(R.id.patient_mobileno);
        mobile_no.setText(umobile);

        type = (TextView) findViewById(R.id.purpose);
        type.setText(utype);
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
            }
        });



        appointment_date = (TextView) findViewById(R.id.patient_date);
        custom_time = (TextView) findViewById(R.id.Patient_time);
        custom_time.setText(utime);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue = spinner.getSelectedItem().toString();
                type.setText(spinnerValue);
               /* if (intent!=null){
                    spinnerValue = intent.getString("type");
                    spinner.setSelection(position);
                }*/
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAppointments.this,
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
                                                   TimePickerDialog timePickerDialog = new TimePickerDialog(EditAppointments.this,
                                                           new TimePickerDialog.OnTimeSetListener() {

                                                               @Override
                                                               public void onTimeSet(TimePicker view, int hourOfDay,
                                                                                     int minute) {

                                                                   custom_time.setText(hourOfDay + ":" + minute);
                                                               }
                                                           }, mHour, mMinute, false);
                                                   timePickerDialog.show();
                                               }
                                           }
                                       }
        );


        Button edit = (Button)findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAppo();
            }
        });
    }

    private void EditAppo() {
        progressDialog.setMessage("Fixing Appointment...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_APPO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.hide();
                    JSONObject object = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), object.getString("response"), Toast.LENGTH_SHORT).show();
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
                params.put("f1",uid);
                params.put("f2",uname);
                params.put("f3",umobile);
                params.put("f4",type.getText().toString());
                params.put("f5",appointment_date.getText().toString());
                params.put("f6",custom_time.getText().toString());
                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);




    }
}


