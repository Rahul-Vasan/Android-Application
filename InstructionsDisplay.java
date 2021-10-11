package com.example.fbans.projecthm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

//detail ins
public class InstructionsDisplay extends AppCompatActivity {

    RecyclerView recyclerView;
    InstructionDisplayAdapter adapter;
    List<Instructions> instructionsList;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_display);
        instructionsList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        if (recyclerView!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        adapter = new InstructionDisplayAdapter();
        recyclerView.setAdapter(adapter);
        loadIns();
        Bundle intent = getIntent().getExtras();
        name = intent.getString("ID");
    }

    private void loadIns() {
        final StringRequest request = new StringRequest(Request.Method.POST, Constants.STAFF_INS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response:" ,"JSON Response");
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++) {
                        Log.i("Loop","Response LOOP");
                        JSONObject object = array.getJSONObject(i);
                        //if (object.getString("staff_name").equalsIgnoreCase(name)) {
                            Log.i("Response:", object.getString("patient_name"));
                            Instructions instructions = new Instructions(object.getString("patient_name"), object.getString("room_no"), object.getString("description"));
                            instructionsList.add(instructions);
                        //}
                    }
                    adapter = new InstructionDisplayAdapter(InstructionsDisplay.this,instructionsList);
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
                params.put("f1",name);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(request);
    }
}
