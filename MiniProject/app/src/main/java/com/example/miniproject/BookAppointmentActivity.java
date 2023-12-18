package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class BookAppointmentActivity extends AppCompatActivity {

    LinearLayout cardiologyDepartment, dermatologyDepartment, ophthalmologyDepartment, generalDepartment, neurologyDepartment, gynecologyDepartment, orthopaedicsDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent i = getIntent();
        String patient_id = i.getStringExtra("patient_id");
        String patient_name = i.getStringExtra("name");
        String patient_phone_no = i.getStringExtra("phone_no");

        cardiologyDepartment = (LinearLayout) findViewById(R.id.cardiologyDepartment);
        cardiologyDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("Cardiology", patient_id, patient_name, patient_phone_no);
            }
        });

        dermatologyDepartment = (LinearLayout) findViewById(R.id.dermatologyDepartment);
        dermatologyDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("Dermatology", patient_id, patient_name, patient_phone_no);
            }
        });

        ophthalmologyDepartment = (LinearLayout) findViewById(R.id.ophthalmologyDepartment);
        ophthalmologyDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("Ophthalmology", patient_id, patient_name, patient_phone_no);
            }
        });

        generalDepartment = (LinearLayout) findViewById(R.id.generalDepartment);
        generalDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("General", patient_id, patient_name, patient_phone_no);
            }
        });

        neurologyDepartment = (LinearLayout) findViewById(R.id.neurologyDepartment);
        neurologyDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("Neurology", patient_id, patient_name, patient_phone_no);
            }
        });

        gynecologyDepartment = (LinearLayout) findViewById(R.id.gynecologyDepartment);
        gynecologyDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("Gynecology", patient_id, patient_name, patient_phone_no);
            }
        });

        orthopaedicsDepartment = (LinearLayout) findViewById(R.id.orthopaedicsDepartment);
        orthopaedicsDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display_doctors("Orthopaedics", patient_id, patient_name, patient_phone_no);
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookAppointmentActivity.this, PatientActivity.class);
                startActivity(intent);
            }
        });
    }

    public void display_doctors(String department, String patient_id, String patient_name, String patient_phone_no) {
        DBHelper db = new DBHelper(getApplicationContext());

        Cursor record = db.retrieve_doctor_from_department(department);
        Intent i = new Intent(BookAppointmentActivity.this, ViewDoctorsActivity.class);
        int count = 0;

        i.putExtra("patient_id", patient_id);
        i.putExtra("patient_name", patient_name);
        i.putExtra("patient_phone", patient_phone_no);
        i.putExtra("department", department);

        while(record.moveToNext())
        {
            String doctor_id = record.getString(0);
            String name = record.getString(1);
            String designation = record.getString(3);
            String gender = record.getString(4);
            String phone_no = record.getString(5);

            i.putExtra("doctor_id_" + String.valueOf(count), doctor_id);
            i.putExtra("name_" + String.valueOf(count), name);
            i.putExtra("designation_" + String.valueOf(count), designation);
            i.putExtra("gender_" + String.valueOf(count), gender);
            i.putExtra("phone_no_" + String.valueOf(count), phone_no);
            count++;
        }

        i.putExtra("record_count", String.valueOf(count));
        startActivity(i);
    }
}