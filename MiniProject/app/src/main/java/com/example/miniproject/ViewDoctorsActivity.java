package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ViewDoctorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewDoctorsActivity.this, PatientActivity.class);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
        String patient_name = intent.getStringExtra("patient_name");
        String patient_id = intent.getStringExtra("patient_id");
        String patient_phone_no = intent.getStringExtra("patient_phone_no");

        String department = intent.getStringExtra("department");
        TextView department_title = (TextView) findViewById(R.id.departmentDoctors);
        department_title.setText(department);

        LinearLayout doctors = (LinearLayout) findViewById(R.id.doctors);
        int count = Integer.parseInt(intent.getStringExtra("record_count"));
        if(count == 0) {
            TextView error = (TextView) findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
            error.setText("No Doctors Available!");
        }
        for(int i = 0; i < count; i++) {
            String doctor_id = intent.getStringExtra("doctor_id_" + String.valueOf(i));
            String name = intent.getStringExtra("name_" + String.valueOf(i));
            String designation = intent.getStringExtra("designation_" + String.valueOf(i));
            String gender = intent.getStringExtra("gender_" + String.valueOf(i));
            String phone_no = intent.getStringExtra("phone_no_" + String.valueOf(i));

            LinearLayout doctor = new LinearLayout(getApplicationContext());
            doctor.setOrientation(LinearLayout.HORIZONTAL);
            doctor.setGravity(Gravity.LEFT);

            ImageView genderImage = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    300, 300); // Adjust width and height as needed
            genderImage.setLayoutParams(imageParams);
            genderImage.setForegroundGravity(Gravity.CENTER_VERTICAL);
            if(gender.equalsIgnoreCase("female"))
                genderImage.setImageResource(R.drawable.female_doctor);
            else
                genderImage.setImageResource(R.drawable.male_doctor);
            doctor.addView(genderImage);

            LinearLayout doctor_details = new LinearLayout(getApplicationContext());
            doctor_details.setOrientation(LinearLayout.VERTICAL);
            doctor_details.setGravity(Gravity.RIGHT);

            TextView doctor_name_tv = new TextView(getApplicationContext());
            doctor_name_tv.setText(name);
            doctor_name_tv.setTextColor(getResources().getColor(R.color.purple));
            doctor_name_tv.setTextSize(24);
            doctor_name_tv.setGravity(Gravity.RIGHT);
            doctor_details.addView(doctor_name_tv);

            TextView doctor_id_tv = new TextView(getApplicationContext());
            doctor_id_tv.setText(doctor_id);
            doctor_id_tv.setTextColor(getResources().getColor(R.color.purple));
            doctor_id_tv.setTextSize(24);
            doctor_id_tv.setGravity(Gravity.RIGHT);
            doctor_details.addView(doctor_id_tv);

            TextView doctor_designation_tv = new TextView(getApplicationContext());
            doctor_designation_tv.setText(designation);
            doctor_designation_tv.setTextColor(getResources().getColor(R.color.purple));
            doctor_designation_tv.setTextSize(24);
            doctor_designation_tv.setGravity(Gravity.RIGHT);
            doctor_details.addView(doctor_designation_tv);

            TextView doctor_phone_no_tv = new TextView(getApplicationContext());
            doctor_phone_no_tv.setText(phone_no);
            doctor_phone_no_tv.setTextColor(getResources().getColor(R.color.purple));
            doctor_phone_no_tv.setTextSize(24);
            doctor_phone_no_tv.setGravity(Gravity.RIGHT);
            doctor_details.addView(doctor_phone_no_tv);

            //Toast.makeText(getApplicationContext(),gender,Toast.LENGTH_SHORT).show();

            doctor.setClickable(true);
            doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1 = new Intent(ViewDoctorsActivity.this, PickDateActivity.class);
                    i1.putExtra("patient_id", patient_id);
                    i1.putExtra("patient_name", patient_name);
                    i1.putExtra("patient_phone", patient_phone_no);
                    i1.putExtra("doctor_id", doctor_id);
                    i1.putExtra("doctor_name", name);
                    startActivity(i1);
                }
            });
            doctor.addView(doctor_details);

            doctors.addView(doctor);
        }
    }
}