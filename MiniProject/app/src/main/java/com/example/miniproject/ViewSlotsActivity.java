package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ViewSlotsActivity extends AppCompatActivity {
    String[] slot_time = {"10", "11", "12", "14", "15", "16", "17", "18", "19", "20"};
    String appointment_slot;
    int avail_slots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSlotsActivity.this, PatientActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String doctor_id = intent.getStringExtra("doctor_id");
        String doctor_name = intent.getStringExtra("doctor_name");
        String patient_id = intent.getStringExtra("patient_id");
        String patient_name = intent.getStringExtra("patient_name");
        String patient_phone_no = intent.getStringExtra("patient_phone_no");
        String appointment_date = intent.getStringExtra("appointment_date");
        String slot_id;
        int count = Integer.parseInt(intent.getStringExtra("record_count"));

        LinearLayout slots = (LinearLayout) findViewById(R.id.slots);
        slots.setOrientation(LinearLayout.VERTICAL);
        if(count == 0) {
            for(int i = 0; i < slot_time.length; i++) {
                Button slot = new Button(getApplicationContext());
                if(Integer.parseInt(slot_time[i]) < 12) {
                    slot.setText(slot_time[i] + "AM");
                }
                else {
                    slot.setText(slot_time[i] + "PM");
                }
                slot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // Toast.makeText(getApplicationContext(),"ERROR DIE",Toast.LENGTH_SHORT).show();
                        avail_slots = 2;
                        appointment_slot = String.valueOf(slot.getText().toString().charAt(0) )+ String.valueOf(slot.getText().toString().charAt(1));
                        Intent intent1 = new Intent(ViewSlotsActivity.this, SymptomsActivity.class);
                        intent1.putExtra("doctor_id", doctor_id);
                        intent1.putExtra("doctor_name", doctor_name);
                        intent1.putExtra("patient_id", patient_id);
                        intent1.putExtra("patient_name", patient_name);
                        intent1.putExtra("appointment_date", appointment_date);
                        intent1.putExtra("appointment_slot", appointment_slot);
                        intent1.putExtra("avail_slots", String.valueOf(avail_slots));
                        intent1.putExtra("patient_phone_no", patient_phone_no);
                        startActivity(intent1);

                    }
                });
                slots.addView(slot);
            }
        }
        else {
            for(int i = 0; i < slot_time.length; i++) {
                for(int j = 0; j < count; j++) {
                    slot_id = intent.getStringExtra("slot_id");
                    Toast.makeText(getApplicationContext(),"COUNT OF DOCTOR IN SAME DATE: "+String.valueOf(count),Toast.LENGTH_SHORT).show();
                    avail_slots = Integer.parseInt(intent.getStringExtra("avail_slots"));

                    if(slot_id.equals(slot_time[i]) && avail_slots != 0) {
                        Button slot = new Button(getApplicationContext());
                        if(Integer.parseInt(slot_time[i]) < 12) {
                            slot.setText(slot_time[i] + "AM");
                        }
                        else {
                            slot.setText(slot_time[i] + "PM");
                        }
                        slot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                avail_slots--;
                                appointment_slot = String.valueOf(slot.getText().toString().charAt(0) )+ String.valueOf(slot.getText().toString().charAt(1));
                                Intent intent2 = new Intent(ViewSlotsActivity.this, SymptomsActivity.class);
                                intent2.putExtra("doctor_id", doctor_id);
                                intent2.putExtra("doctor_name", doctor_name);
                                intent2.putExtra("patient_id", patient_id);
                                intent2.putExtra("patient_name", patient_name);
                                intent2.putExtra("appointment_date", appointment_date);
                                intent2.putExtra("appointment_slot", appointment_slot);
                                intent2.putExtra("avail_slots", String.valueOf(avail_slots));
                                intent2.putExtra("patient_phone", patient_phone_no);
                                startActivity(intent2);
                            }
                        });
                        slots.addView(slot);
                    }
                }
            }
        }
    }
}