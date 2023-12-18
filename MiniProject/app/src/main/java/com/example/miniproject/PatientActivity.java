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

public class PatientActivity extends AppCompatActivity {

    String user_id,name,patient_id,patient_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(PatientActivity.this,
                        LoginActivity.class);
            startActivity(i);
         }
        });

        Intent i = getIntent();
        user_id = i.getStringExtra("user_id");
        DBHelper db = new DBHelper(getApplicationContext());
        Cursor patient_record = db.retrieve_patient(user_id);
        if(patient_record.getCount() <= 0) {
            showErrorDialog("Couldn't retrieve patient details");
            Intent i1 = new Intent(PatientActivity.this, LoginActivity.class);
            startActivity(i1);
        }
        else {
            while (patient_record.moveToNext()) {
                name = patient_record.getString(2);
                patient_id = patient_record.getString(1);
                patient_phone = patient_record.getString(6);
            }
        }

        TextView welcome = (TextView) findViewById(R.id.welcomePatientText);
        welcome.setText("WELCOME "+name+" !");
        Button viewAppointment = (Button) findViewById(R.id.viewAppointment);
        Button bookAppointment = (Button) findViewById(R.id.bookAppointment);

        viewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),"Viewing appointment details",Toast.LENGTH_SHORT).show();

                DBHelper db = new DBHelper(getApplicationContext());
                Cursor appointment_details = db.retrieve_patient_appointments(patient_id);
                String record = "";
                //Toast.makeText(getApplicationContext(),"appointment details available",Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(PatientActivity.this, ViewPatientAppointmentActivity.class);
                while(appointment_details.moveToNext()) {
                    Toast.makeText(getApplicationContext(), "Inside while", Toast.LENGTH_SHORT).show();
                    record += "Doctor Name: " + appointment_details.getString(3) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "Date: " + appointment_details.getString(4) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "Time: " + appointment_details.getString(5) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "Symptoms: " + appointment_details.getString(6) + "\n";
                    System.out.println("APPOINTMENT RECORD: " + record);
                    record += "\n\n";
                    //Toast.makeText(getApplicationContext(), "String appt date: " + appointment_details.getString(4), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "APPOINTMENT RECORD: " + record, Toast.LENGTH_SHORT).show();
                    i2.putExtra("appointment_details", record);
                }
                startActivity(i2);
            }
        });
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(PatientActivity.this, BookAppointmentActivity.class);
                i2.putExtra("patient_id", patient_id);
                i2.putExtra("name", name);
                i2.putExtra("patient_phone",patient_phone);
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