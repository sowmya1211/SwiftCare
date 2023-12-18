package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,
                        LoginActivity.class);
                startActivity(i);
            }
        });

        Button insert = (Button) findViewById(R.id.button_insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,
                        InsertDoctorActivity.class);
                startActivity(i);
            }
        });

        Button delete = (Button) findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,
                        DeleteDoctorActivity.class);
                startActivity(i);
            }
        });

        Button retrieve_all = (Button)
                findViewById(R.id.button_retrieve_all);
        retrieve_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(getApplicationContext());
                Cursor record = db.retrieve_all_records();
                String record_details = "";
                while(record.moveToNext()) {
                    record_details += "ID: " + record.getString(0) + "\n";
                    record_details += "Name: " + record.getString(1) + "\n";
                    record_details += "Department: " + record.getString(2) + "\n";
                    record_details += "Designation: " + record.getString(3) + "\n";
                    record_details += "Gender: " + record.getString(4) + "\n";
                    record_details += "Phone Number: " + record.getString(5) + "\n\n";
                }
                Intent i = new Intent(AdminActivity.this,
                        DisplayDoctorActivity.class);
                i.putExtra("record_details", record_details);
                startActivity(i);
            }
        });
    }
}