package com.example.fbans.projecthm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fbans.projecthm.utils.Constants;
import com.example.fbans.projecthm.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText name,mobileno, password,age;
    RadioGroup gendergroup;
    RadioButton male, female;

    Spinner spinner;

    Button register, signin;
    String gender;
    String spinnerValue;
    boolean  noconn = false;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(SharedPreference.getInstance(this).isLoggedIn()) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("hospmanagepref", Context.MODE_PRIVATE);
            String temp = sharedPreferences.getString("keytype",null);

            if (temp.isEmpty() ){
                Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(Register.this,MainActivity.class);
                intent.putExtra("spinnerValue",temp);
                startActivity(intent);
                finish();
            }
        }

        name = findViewById(R.id.name);
        mobileno = findViewById(R.id.mobileno);
        password = findViewById(R.id.password);
        age = findViewById(R.id.age);
        gendergroup = findViewById(R.id.radioGroup);
        register = findViewById(R.id.register);
        signin = findViewById(R.id.signin);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);

        spinner = findViewById(R.id.spinnerReg);
        progressDialog = new ProgressDialog(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()){
                    registerUser();
                }
                else {
                    noconn=true;
                    new AlertDialog.Builder(Register.this)
                            .setTitle("Closing the App")
                            .setMessage("No Internet Connection,check your settings")
                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Register.this,LoginActivity.class));
            }
        });
    }

    private void  registerUser(){
        final String uname = name.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        final String mno = mobileno.getText().toString().trim();
        final String usage = age.getText().toString().trim();

        if (name.getText().toString().equals("")|| name.getText().toString().length() < 3)
            Toast.makeText(getApplicationContext(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if(!(name.getText().toString().matches("^[a-zA-Z\\s]*$")))
            Toast.makeText(getApplicationContext(), "Invalid name", Toast.LENGTH_SHORT).show();
        else if (mobileno.getText().toString().equals("") || mobileno.getText().toString().length() != 10)
            Toast.makeText(getApplicationContext(), "Invalid phoneno", Toast.LENGTH_SHORT).show();
        else if (password.getText().toString().equals("") || password.getText().toString().length() < 6)
            Toast.makeText(getApplicationContext(), "Password should atleast contain 4 characters", Toast.LENGTH_SHORT).show();
        else if (age.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Enter a age", Toast.LENGTH_SHORT).show();
        else if (male.isChecked() || female.isChecked()) {
            if(male.isChecked())
                gender ="male";
            if (female.isChecked())
                gender ="female";
        }


        // else {
        progressDialog.setMessage("Registering..");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REG_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("re").equalsIgnoreCase("success !")){
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    intent.putExtra("spinnerValue",spinnerValue);
                    startActivity(intent);
                    finish();
                    }else {
                        Toast.makeText(getApplicationContext(),jsonObject.getString("re"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                progressDialog.dismiss();
                Log.i("Volley Error",volleyError.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("f1",spinnerValue);
                params.put("f2",uname);
                params.put("f3",mno);
                params.put("f4",pwd);
                params.put("f5",usage);
                params.put("f6",gender);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //}

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
