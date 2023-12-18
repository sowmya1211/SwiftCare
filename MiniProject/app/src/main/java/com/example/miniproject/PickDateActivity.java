package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PickDateActivity extends AppCompatActivity
{
    private DatePickerDialog datePickerDialog;
    private Button dateButton, back, pickSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickDateActivity.this, PatientActivity.class);
                startActivity(intent);
            }
        });

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        Intent i = getIntent();
        String patient_id = i.getStringExtra("patient_id");
        String patient_name = i.getStringExtra("patient_name");
        String patient_phone_no = i.getStringExtra("patient_phone_no");
        String doctor_id = i.getStringExtra("doctor_id");
        String doctor_name = i.getStringExtra("doctor_name");

        pickSlot = (Button) findViewById(R.id.pickSlot);
        pickSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(PickDateActivity.this, ViewSlotsActivity.class);
                String appointment_date = dateButton.getText().toString();
                i1.putExtra("appointment_date", appointment_date);
                Toast.makeText(getApplicationContext(), "BEFORE SLOT BOOK "+appointment_date,Toast.LENGTH_SHORT).show();
                i1.putExtra("patient_id", patient_id);
                i1.putExtra("patient_name", patient_name);
                i1.putExtra("patient_phone", patient_phone_no);
                i1.putExtra("doctor_id", doctor_id);
                i1.putExtra("doctor_name", doctor_name);

                DBHelper db = new DBHelper(getApplicationContext());

                Cursor record = db.retrieve_slots(doctor_id, appointment_date);
                int count = 0;

                while(record.moveToNext())
                {
                    String doctor_id = record.getString(0);
                    String slot_id = record.getString(1);
                    String date = record.getString(2);
                    String avail_slots = record.getString(3);

                    i1.putExtra("slot_id" + String.valueOf(count), slot_id);
                    i1.putExtra("avail_slots" + String.valueOf(count), avail_slots);
                    count++;
                }

                i1.putExtra("record_count", String.valueOf(count));
                startActivity(i1);
            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month)+ " " +day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}