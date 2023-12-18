package com.example.miniproject;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    String user_id,password,name,age,gender,address,phoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView loginLink = (TextView) findViewById(R.id.loginText);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText u_id = (EditText) findViewById(R.id.userid);
                user_id = u_id.getText().toString();
                EditText pass = (EditText) findViewById(R.id.password);
                password = pass.getText().toString();
                EditText fullname = (EditText) findViewById(R.id.name);
                name = fullname.getText().toString();
                EditText agee = (EditText) findViewById(R.id.age);
                age = agee.getText().toString();
                EditText addrs = (EditText) findViewById(R.id.address);
                address = addrs.getText().toString();
                EditText phno = (EditText) findViewById(R.id.phoneno);
                phoneno = phno.getText().toString();

                RadioGroup rg_gender = findViewById(R.id.gender);
                int rid = rg_gender.getCheckedRadioButtonId();
                if(rid == -1)
                    gender = "None Selected";
                else if(rid == R.id.radio_button_female)
                    gender = "Female";
                else if(rid == R.id.radio_button_male)
                    gender = "Male";
                else if(rid == R.id.radio_button_others)
                    gender = "Others";


                String regex = "^[a-zA-Z0-9@#%_]{5,}$"; //Validate username - 5 or more characters
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(user_id);
                if(!matcher.matches())
                    showErrorDialog("Username must be atleast 5 characters long");
                else{
                    regex = "^(?=.*[0-9])(?=.*[@#$%^&_])(?=.*[a-z])(?=.*[A-Z]).{8,}$"; //Password strength
                    Pattern pattern1 = Pattern.compile(regex);
                    Matcher matcher1 = pattern1.matcher(password);
                    if(!matcher1.matches())
                        showErrorDialog("Weak Password! \n Password must be atleast 8 characters long \n" +
                                "with 1 digit, special character, uppercase and lowercase");
                    else{
                        //Insert Details
                        DBHelper db = new DBHelper(getApplicationContext());
                        Boolean check_insert = db.insert_user(user_id,password, name, age,gender,address,phoneno);
                        if(!check_insert) {
                            showErrorDialog("Couldn't insert details! Username already exists!");
                            u_id.setText("");
                            pass.setText("");
                            fullname.setText("");
                            agee.setText("");
                            addrs.setText("");
                            phno.setText("");
                            rg_gender.clearCheck();
                        }
                            else {
                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            i.putExtra("user_id", user_id);
                            startActivity(i);
                        }
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