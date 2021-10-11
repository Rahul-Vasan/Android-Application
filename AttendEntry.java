package com.example.fbans.projecthm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AttendEntry extends Fragment {
    View vi;
    EditText staffname;
    TextView date;
    RadioGroup attendance;
    RadioButton present;
    RadioButton absent;
    Button addentry;
    private int mYear, mMonth, mDay;
    String attendance1;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_attendent,container,false);

        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        staffname = vi.findViewById(R.id.staffname);
        date = vi.findViewById(R.id.date);
        attendance =vi.findViewById(R.id.radioGroup);
        present = vi.findViewById(R.id.radioButton);
        absent = vi.findViewById(R.id.radioButton2);
        addentry = vi.findViewById(R.id.button);
        progressDialog=new ProgressDialog(getContext());

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date.setText(new StringBuilder().append(mDay).append(" ").append("-").append(mMonth + 1).append("-")
                .append(mYear));
        addentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceEntry();
            }
        });

                /*DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();*/

    }

    private void attendanceEntry() {

        final String sname = staffname.getText().toString();
        final String adate  = date.getText().toString();

        if (staffname.getText().toString().equals("") || staffname.getText().toString().length() < 3)
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if(!(staffname.getText().toString().matches("[a-zA-Z][a-zA-Z]*")))
            Toast.makeText(getActivity(), "Invalid name", Toast.LENGTH_SHORT).show();

        else if(date.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Enter todays date", Toast.LENGTH_SHORT).show();
        date.requestFocus();}
        else if (present.isChecked() || absent.isChecked()) {
            if(present.isChecked())
                attendance1 ="present";
            else
                attendance1 ="absent";


        }
        //else {
            progressDialog.setMessage("Adding...");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, Constants.ADD_ATTENDANCE_URL, new Response.Listener<String>() {
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
                    params.put("e1",sname);
                    params.put("e2",adate);
                    params.put("e3",attendance1);
                    return params;
                }
            };

            RequestHandler.getInstance(getContext()).addToRequestQueue(request);
        //}

    }
}
