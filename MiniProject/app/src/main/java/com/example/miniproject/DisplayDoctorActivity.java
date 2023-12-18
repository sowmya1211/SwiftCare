package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class DisplayDoctorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_display);
        TextView record = (TextView) findViewById(R.id.record);
        Intent i = getIntent();
        String record_display = i.getStringExtra("record_details");
        record.setText(record_display);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DisplayDoctorActivity.this,
                        AdminActivity.class);
                startActivity(i);
            }
        });

    }
}