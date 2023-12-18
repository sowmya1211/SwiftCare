package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class InsertDoctorActivity extends AppCompatActivity {
    String id = "";
    String name = "";
    String department = "";
    String designation = "";
    String[] departments = {"--Select--", "Cardiology", "Dermatology",
            "Ophthalmology", "General", "Neurology", "Gynecology","Orthopaedics"};
    String gender = "";
    String phone = "";

    //CONTINUE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_insert);

        EditText doctor_name = (EditText)
                findViewById(R.id.entered_doctor_name);

        Spinner department_name = (Spinner)
                findViewById(R.id.entered_department_name);
        ArrayAdapter sp_item = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                departments);
        sp_item.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        department_name.setAdapter(sp_item);

        EditText doctor_designation = (EditText)
                findViewById(R.id.entered_doctor_designation);

        EditText doctor_phone = (EditText)
                findViewById(R.id.entered_doctor_phone);

        //TextView error = (TextView) findViewById(R.id.error);
        //error.setText("");
        Button cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InsertDoctorActivity.this,
                        AdminActivity.class);
                startActivity(i);
            }
        });

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = doctor_name.getText().toString();
                department = department_name.getSelectedItem().toString();
                designation = doctor_designation.getText().toString();

                RadioGroup doctor_gender = findViewById(R.id.gender);
                int rid = doctor_gender.getCheckedRadioButtonId();
                if(rid == -1)
                    gender = "None Selected";
                else if(rid == R.id.genderfemale)
                    gender = "Female";
                else if(rid == R.id.gendermale)
                    gender = "Male";

                phone = doctor_phone.getText().toString();

                DBHelper db = new DBHelper(getApplicationContext());
                Boolean check_insert = db.insert_doctor_record(name,
                        department, designation, gender, phone);
                String x = id + " " + name + " " + department+ " " +
                        designation + " " + gender+ " " +phone;
                if (check_insert == true) {
                    Intent j = new Intent(InsertDoctorActivity.this, AdminActivity.class);
                    startActivity(j);
                } else {
                    //error.setText("Record could not be inserted!");
                    showErrorDialog("Could Not Insert Doctor Details!");
                    doctor_name.setText("");
                    department_name.setAdapter(sp_item);
                    doctor_gender.clearCheck();
                    doctor_designation.setText("");
                    doctor_phone.setText("");
                }
                // }
                //}
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