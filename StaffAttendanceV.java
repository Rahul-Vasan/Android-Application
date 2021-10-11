package com.example.fbans.projecthm;

import android.app.DatePickerDialog;
import android.content.Intent;
//import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StaffAttendanceV extends Fragment {
    View vi;
    Button submit ;
    EditText staffname;
    TextView fromDateEtxt;
    TextView toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        vi = inflater.inflate(R.layout.layout_staffattenview,container,false);

        return vi;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        submit = vi.findViewById(R.id.button);
        staffname = vi.findViewById(R.id.staffname);
        findViewsById();

        setDateTimeField();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AttendenceView();

            }
        });
    }

    private void AttendenceView() {
        String sname = staffname.getText().toString();
        String from = fromDateEtxt.getText().toString();
        String to = toDateEtxt.getText().toString();
        if (staffname.getText().toString().equals(""))
            Toast.makeText(getActivity(), "Enter a staff name", Toast.LENGTH_SHORT).show();
        else if (!(staffname.getText().toString().matches("[a-zA-Z][a-zA-Z]*")))
            Toast.makeText(getActivity(), "Invalid staff name", Toast.LENGTH_SHORT).show();
        else if (fromDateEtxt.getText().toString().equals("") || toDateEtxt.getText().toString().equals(""))
            Toast.makeText(getActivity(), "Enter the from and to dates", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getActivity(), "Redirecting....", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), DocStaffAttendanceDisplay.class);
            intent.putExtra("name",sname);
            intent.putExtra("from",from);
            intent.putExtra("to",to);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void findViewsById() {
        fromDateEtxt = (TextView) vi.findViewById(R.id.fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (TextView) vi.findViewById(R.id.todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }


        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);

        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDatePickerDialog.show();
            }
        });
        toDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog.show();
            }
        });
    }


}
