package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class DoctorActivity extends AppCompatActivity {

    String doctor_id,doctor_name,doctor_designation,doctor_department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoctorActivity.this,
                        LoginActivity.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        doctor_id = i.getStringExtra("doctor_id");
        doctor_name = i.getStringExtra("doctor_name");
        doctor_designation = i.getStringExtra("doctor_designation");
        doctor_department = i.getStringExtra("doctor_department");

        TextView welcome = (TextView) findViewById(R.id.welcomeDoctorText);
        welcome.setText("WELCOME "+doctor_name+" !\n");
        TextView welcomeDets = (TextView) findViewById(R.id.welcomeDoctorDetails);
        welcomeDets.setText(doctor_designation+" , "+doctor_department);
        Button viewAppointment = (Button) findViewById(R.id.viewAppointment);

        viewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper db = new DBHelper(getApplicationContext());
                Cursor appointment_details = db.retrieve_doctor_appointments(doctor_id);
                String record = "";
                //Toast.makeText(getApplicationContext(),"appointment details available",Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(DoctorActivity.this, ViewDoctorAppointmentActivity.class);
                while(appointment_details.moveToNext()) {
                    Toast.makeText(getApplicationContext(), "Inside while", Toast.LENGTH_SHORT).show();
                    record += "Patient ID: " + appointment_details.getString(0) + "\n";
                    record += "Patient Name: " + appointment_details.getString(1) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "Date: " + appointment_details.getString(4) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "Time: " + appointment_details.getString(5) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "Patient Symptoms: " + appointment_details.getString(6) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "\n\n";
                    //Toast.makeText(getApplicationContext(), "String appt date: " + appointment_details.getString(4), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "APPOINTMENT RECORD: " + record, Toast.LENGTH_SHORT).show();
                    i2.putExtra("appointment_details", record);
                    i2.putExtra("doctor_id",doctor_id);
                    i2.putExtra("doctor_name",doctor_name);
                    i2.putExtra("doctor_designation",doctor_designation);
                    i2.putExtra("doctor_department",doctor_department);
                }
                startActivity(i2);
            }
        });
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