package com.example.fbans.projecthm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fbans.projecthm.utils.Absent;
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

public class DocStaffAttendanceDisplay extends AppCompatActivity {
    String name,from,to;
    RecyclerView recyclerView;
    AttendanceAdapter attendanceAdapter;
    List<Absent> absentList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_staff_attendance_display);

        Bundle intent = getIntent().getExtras();
        name = intent.getString("name");
        from = intent.getString("from");
        to = intent.getString("to");
        absentList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.absenties);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(attendanceAdapter);
        absentDet();
    }

    public void absentDet() {
        progressDialog.setMessage("Adding...");
        progressDialog.show();
        final StringRequest request = new StringRequest(Request.Method.POST, Constants.VIEW_ATTENDANCE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        Absent absent = new Absent(object.getString("5"));
                        absentList.add(absent);
                    }
                    attendanceAdapter = new AttendanceAdapter(getApplicationContext(),absentList);
                    recyclerView.setAdapter(attendanceAdapter);
                    attendanceAdapter.notifyDataSetChanged();
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
                params.put("f1",name);
                params.put("f2",from);
                params.put("f3",to);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }
}
