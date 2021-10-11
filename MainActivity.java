package com.example.fbans.projecthm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbans.projecthm.utils.SharedPreference;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String type,id;
    boolean doubleBackToExitPressedOnce = false,noconn = false;
    Bundle intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       if (!SharedPreference.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
           finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        intent = getIntent().getExtras();
        type = intent.getString("spinnerValue");

        if (type.equalsIgnoreCase("doctor")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.doctor_drawer);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_frame, new DocViewAppo())
                    .commit();
        } else if (type.equalsIgnoreCase("staff")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.staff_drawer);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_frame, new PatDetails())
                    .commit();
        } else if (type.equalsIgnoreCase("patient")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.patient_drawer);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_frame, new AbtHospital())
                    .commit();

        } else if (type.equalsIgnoreCase("staff head")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.staffhead_drawer);
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.main_frame, new AttendEntry())
                    .commit();
        }

        View header = navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.user);
        SharedPreferences sharedPreferences = this.getSharedPreferences("hospmanagepref", Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString("keyusername",null);
        name.setText(temp);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SharedPreference.getInstance(this).logout();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
            int id = item.getItemId();
            FragmentManager fm = getSupportFragmentManager();
            switch (id) {
                case R.id.abouthosp:
                    fm.beginTransaction().replace(R.id.main_frame, new AbtHospital()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.aboutdoctor:
                    fm.beginTransaction().replace(R.id.main_frame, new AbtDoctor()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.fixappointment:
                    fm.beginTransaction().replace(R.id.main_frame, new FixAppoint()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.patient_details:
                    fm.beginTransaction().replace(R.id.main_frame, new PatDetails()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.docviewappointment:
                    fm.beginTransaction().replace(R.id.main_frame, new DocViewAppo()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.instructiontostaff1:
                    fm.beginTransaction().replace(R.id.main_frame, new InstructiontoSta()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.staffdetails1:
                    fm.beginTransaction().replace(R.id.main_frame, new StaDetails()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.staffattendanceview:
                    fm.beginTransaction().replace(R.id.main_frame, new StaffAttendanceV()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.instructiontostaffdisplay:
                    fm.beginTransaction().replace(R.id.main_frame, new InstructionToStaffDisp()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.attendanceentry:
                    fm.beginTransaction().replace(R.id.main_frame, new AttendEntry()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.staffviewappointment:
                    fm.beginTransaction().replace(R.id.main_frame, new StaffViewAppo()).commit();
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
                case R.id.nav_manage:
                    Intent intent = new Intent(this, MapActivity1.class);
                    startActivity(intent);
                    if (!isNetworkAvailable()){
                        noconn=true;
                        new AlertDialog.Builder(MainActivity.this)
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
                    break;
            }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
