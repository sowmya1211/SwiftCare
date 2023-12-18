package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.widget.TextView;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class ViewDoctorAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdoctorappointment);

        Intent i = getIntent();
        String appointment_details = i.getStringExtra("appointment_details");
        String doctor_id = i.getStringExtra("doctor_id");
        String doctor_name = i.getStringExtra("doctor_name");
        String doctor_designation = i.getStringExtra("doctor_designation");
        String doctor_department = i.getStringExtra("doctor_department");

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(ViewDoctorAppointmentActivity.this,
                        DoctorActivity.class);
                i1.putExtra("doctor_id",doctor_id);
                i1.putExtra("doctor_name",doctor_name);
                i1.putExtra("doctor_designation",doctor_designation);
                i1.putExtra("doctor_department",doctor_department);
                startActivity(i1);
            }
        });

        TextView appt = (TextView) findViewById(R.id.yourappointment);
        appt.setText(appointment_details);
    }
    public static int compareCurrentDate(String apptdate) {
        String a_dates[] = apptdate.split(" ");
        int a_m = getMonthFormat(a_dates[0]);
        int a_d = Integer.parseInt(a_dates[1]);
        int a_y = Integer.parseInt(a_dates[2]);
        //Current Date - month day year
        DateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        Calendar obj = Calendar.getInstance();
        String curr = formatter.format(obj.getTime());
        String c_dates[] = curr.split(" ");
        int c_m = getMonthFormat(c_dates[0]);
        int c_d = Integer.parseInt(c_dates[1]);
        int c_y = Integer.parseInt(c_dates[2]);
        if(c_y > a_y)
            return -1;
        if(c_y == a_y && c_m > a_m)
            return -1;
        if(c_y == a_y && c_m == a_m && c_d > a_d)
            return -1;
        return 1;
    }
    public static String[] appendToStringArray(String[] array, String element) {
        // Create a new array with a length one greater than the original array
        String[] newArray = Arrays.copyOf(array, array.length + 1);
        // Assign the new element to the last index of the new array
        newArray[array.length] = element;
        return newArray;
    }
    public static int getMonthFormat(String month)
    {
        if(month == "JAN")
            return 1;
        if(month == "FEB")
            return 2;
        if(month == "MAR")
            return 3;
        if(month == "APR")
            return 4;
        if(month == "MAY")
            return 5;
        if(month == "JUN")
            return 6;
        if(month == "JUL")
            return 7;
        if(month == "AUG")
            return 8;
        if(month == "SEP")
            return 9;
        if(month == "OCT")
            return 10;
        if(month == "NOV")
            return 11;
        if(month == "DEC")
            return 12;

        //default should never happen
        return 1;
    }
    private void showErrorDialog(String errorMessage) {
        // Create a custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_error_dialog);
        dialog.setCancelable(true);

        // Set the error message
        TextView errorMessageTextView = dialog.findViewById(R.id.errorMessage);
        errorMessageTextView.setText(errorMessage);

        // Set a listener to dismiss the dialog when clicked
        dialog.findViewById(R.id.errorIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

}