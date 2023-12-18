package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class DeleteDoctorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_delete);

        //TextView error = (TextView) findViewById(R.id.error);
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText doctor_id = (EditText)
                        findViewById(R.id.doctor_id);
                String id = doctor_id.getText().toString();
                DBHelper db = new DBHelper(getApplicationContext());
                Boolean check = db.delete_doctor_record(id);
                if (check == true) {
                    Intent i = new Intent(DeleteDoctorActivity.this,
                            AdminActivity.class);
                    startActivity(i);
                } else {
                    //error.setText("Could not delete!");
                    showErrorDialog("Could Not Delete Doctor Details!");
                    doctor_id.setText("");

                }
            }
        });
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeleteDoctorActivity.this,
                        AdminActivity.class);
                startActivity(i);
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