package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signupLink = (TextView) findViewById(R.id.signupText);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText u_id = (EditText) findViewById(R.id.userid);
                String user_id = u_id.getText().toString();
                EditText pass = (EditText) findViewById(R.id.password);
                String password = pass.getText().toString();
                DBHelper db = new DBHelper(getApplicationContext());
                if(user_id.equals("admin")){
                    if(password.equals("sps@287")){
                        Intent i = new Intent(LoginActivity.this, AdminActivity.class); //Change AdminActivity
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                    }
                    else
                        showErrorDialog("Incorrect Password!");
                }
                else if(user_id.charAt(0)=='D'){ //Doctor
                    Cursor doc_rec = db.retrieve_docdetails(user_id);
                    if(doc_rec.getCount()>=1) {
                        int f = 0;
                        Intent i = new Intent(LoginActivity.this, DoctorActivity.class); //Change AdminActivity
                        while (doc_rec.moveToNext()) {
                            Toast.makeText(getApplicationContext(), "Doc exists: " + user_id, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Doc Name: " + doc_rec.getString(1), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Doc Phno: " + doc_rec.getString(5), Toast.LENGTH_LONG).show();
                            if (password.equals(doc_rec.getString(5))) {
                                Toast.makeText(getApplicationContext(), "Doc ID: " + user_id, Toast.LENGTH_SHORT).show();
                                i.putExtra("doctor_id", user_id);
                                i.putExtra("doctor_name", doc_rec.getString(1));
                                i.putExtra("doctor_designation", doc_rec.getString(3));
                                i.putExtra("doctor_department", doc_rec.getString(2));
                            } else {
                                Toast.makeText(getApplicationContext(), "Doctor password wrong", Toast.LENGTH_SHORT).show();
                                showErrorDialog("Incorrect Password!");
                                f = 1;
                            }
                        }
                        if(f==0)
                            startActivity(i);
                    }
                    else{
                            Toast.makeText(getApplicationContext(), "Doctor username wrong", Toast.LENGTH_SHORT).show();
                            showErrorDialog("Username doesn't exist!");
                        }
                    }
                else {
                    int c = db.validate_user(user_id,password);
                    if(c==-1){  //Invalid username
                        showErrorDialog("Username doesn't exist!");
                    }
                    else if(c==0){ //Invalid password
                        showErrorDialog("Incorrect Password!");
                    }
                    else if(c==1) {
                        Intent i = new Intent(LoginActivity.this, PatientActivity.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                    }
                }
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