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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mobileno;
    EditText password;
    Button login;
    Button signup;
    Spinner spinner;
    String spinnerValue;
    ProgressDialog progressDialog;
    public boolean noconn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPreference.getInstance(this).isLoggedIn()) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("hospmanagepref",Context.MODE_PRIVATE);
            String temp = sharedPreferences.getString("keytype",null);

            if (temp.isEmpty() ){
                Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("spinnerValue",temp);
                startActivity(intent);
                finish();
            }
        }

        mobileno = findViewById(R.id.txt);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        spinner = findViewById(R.id.spin);

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


        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()) {
                    loginUser();
                }else {
                    noconn=true;
                    new AlertDialog.Builder(LoginActivity.this)
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
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,Register.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void loginUser(){

        final String mbno = mobileno.getText().toString();
        final String pass = password.getText().toString();

        if (password.getText().toString().equals("") || password.getText().toString().length() < 4) {
            Toast.makeText(getApplicationContext(), "Password should be more than 4 characters", Toast.LENGTH_SHORT).show();
        } else if (mobileno.getText().toString().equals("") || mobileno.getText().toString().length() != 10) {
            Toast.makeText(getApplicationContext(), "Enter a correct mobile no", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Login_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("re").equalsIgnoreCase("register")){

                        SharedPreference.getInstance(getApplicationContext())
                                .userLogin(
                                        jsonObject.getInt("0"),
                                        jsonObject.getString("1"),
                                        jsonObject.getString("2"),
                                        jsonObject.getString("3"),
                                        jsonObject.getString("5"),
                                        jsonObject.getString("6")
                                );
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("spinnerValue",spinnerValue);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(
                                getApplicationContext(),
                                jsonObject.getString("re"),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(
                        getApplicationContext(),
                        volleyError.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("f1",spinnerValue);
                params.put("f3",mbno);
                params.put("f4",pass);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
